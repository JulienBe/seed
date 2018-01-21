package be.julien.seed.physics

import be.julien.seed.basics.Dimension
import be.julien.seed.basics.Thing
import be.julien.seed.basics.WallAO
import be.julien.seed.graphics.Drawable
import be.julien.seed.physics.shapes.Circle
import be.julien.seed.physics.shapes.SquareAO
import be.julien.seed.utils.SeedLoggerImpl

object Physics {

    val rollBackSteps = 4
    val rollBackPrecision = ((1f / rollBackSteps) + 0.1f)

    fun angle(to: Thing, from: Thing): Float = angle(to.centerX, to.centerY, from.centerX, from.centerY)
    fun angle(from: Dot, to: Dot): Float = Vec2.tmp.set(to.x - from.x, to.y - from.y).angle
    fun angle(xTo: Float, yTo: Float, xFrom: Float, yFrom: Float): Float = Vec2.tmp.set(xTo - xFrom, yTo - yFrom).angle

    fun checkCollision(a: Thing, b: Thing): Boolean {
        when(a.shape) {
            SquareAO -> return squareCheck(a, b)
            Circle -> return circleCheck(a, b)
        }
        return noCollision(a, b)
    }

    private fun noCollision(a: Thing, b: Thing): Boolean {
        SeedLoggerImpl.error("Hu, collision mistake between $a (${a.shape}) and $b (${b.shape})")
        return false
    }

    private fun circleCheck(circle: Thing, b: Thing): Boolean {
        when(b.shape) {
            SquareAO -> return squareCircle(b.centerX, b.centerY, circle.centerX, circle.centerY, b.dimension, circle.dimension)
            Circle -> return circleCircle(b, circle)
        }
        return noCollision(circle, b)
    }

    private fun circleCircle(a: Thing, b: Thing): Boolean {
        return a.pos.dst(b.pos) < a.hw + b.hw
    }

    private fun squareCheck(square: Thing, b: Thing): Boolean {
        when(b.shape) {
            SquareAO -> return squareSquare(square, b)
            Circle -> return squareCircle(square, b)
        }
        return noCollision(square, b)
    }

    private fun squareSquare(me: Thing, other: Thing): Boolean {
        return me.x < other.x + other.w &&
                me.x + me.w > other.x &&
                me.y < other.y + other.h &&
                me.h + me.y > other.y
    }

    private fun squareCircle(square: Thing, circle: Thing): Boolean {
        val collision = squareCircle(square.centerX, square.centerY, circle.centerX, circle.centerY, square.dimension, circle.dimension)
        if (!collision) {
            // for the moment, circles only are moving fast and colliding aggainst brics
            if (circle.pos.dstFromPrevious > circle.w)
                return squareCircle(square.centerX, square.centerY, circle.pos.midX + circle.hw, circle.pos.midY + circle.hh, square.dimension, circle.dimension)
        }
        return collision
    }

    private fun squareCircle(
            squareCenterX: Float, squareCenterY: Float,
            circleCenterX: Float, circleCenterY: Float,
            squareDim: Dimension, circleDim: Dimension): Boolean {
        val distX = Math.abs(circleCenterX - squareCenterX)
        val distY = Math.abs(circleCenterY - squareCenterY)
        if (distX > (circleDim.halfWidth + squareDim.halfWidth))
            return false
        if (distY > (circleDim.halfHeight + squareDim.halfHeight))
            return false
        if (distX <= squareDim.halfWidth)
            return true
        if (distY <= squareDim.halfHeight)
            return true
        val dx = distX - squareDim.halfWidth
        val dy = distY - squareDim.halfHeight
        return (dx * dx + dy * dy) <= (circleDim.halfWidth * circleDim.halfWidth)
    }

    public fun vecInsideSquare(d: Drawable, v: Vec2): Boolean {
        return d.x < v.x &&
                d.x + d.w > v.x &&
                d.y < v.y &&
                d.h + d.y > v.y
    }

    private fun vecInsideCircle(t: Thing, vec2: Vec2): Boolean = vec2.dst(t.centerX, t.centerY) < t.w

    fun goAwayMod(other: Thing, me: Thing): Int {
        return if (cwCloser(other, me))
            1
        else
            -1
    }

    fun cwCloser(other: Thing, me: Thing): Boolean {
        // ccw 1
        // dir x = -y;
        // dir y = x;
        val leftX = (other.pos.x + other.dir.x) - (me.pos.x - me.dir.y)
        val leftY = (other.pos.y + other.dir.y) - (me.pos.y + me.dir.x)
        // cw -1
        // dir x = y;
        // dir y = -x;
        val rightX = (other.pos.x + other.dir.x) - (me.pos.x + me.dir.y)
        val rightY = (other.pos.y + other.dir.y) - (me.pos.y - me.dir.x)
        return (leftX * leftX) + (leftY * leftY) > (rightX * rightX) + (rightY * rightY)
    }

    fun resolveOverlap(mover: Thing, obstacle: Thing) {
        when (obstacle) {
            is WallAO -> mover.onWallHit().invoke(mover, obstacle)
        }
    }

    fun setOnWall(mover: Thing, obstacle: WallAO) {
        val intersection = getWallLine(obstacle, mover)
        placeOnWall(mover, obstacle, intersection)
    }

    private fun placeOnWall(mover: Thing, obstacle: WallAO, intersection: Pair<Line, Dot>) {
        when (mover.shape) {
            is Circle -> setAlongWall(mover, intersection, mover.hw)
            is SquareAO -> setAlongWall(mover, intersection, mover.hw * 1.5f)
        }
    }

    private fun setAlongWall(mover: Thing, intersection: Pair<Line, Dot>, wallOffset: Float) {
        if (intersection.second == Dot.nowhere)
//            mover.pos.invalidate()
        else {
            mover.dir.validate()
            mover.dir.set(intersection.first.bounceVector.x, intersection.first.bounceVector.y)
            mover.dir.scl(wallOffset)
                // line intersection is based on center
            mover.setCenter(
                    intersection.second.x + mover.hw * intersection.first.bounceVector.x,
                    intersection.second.y + mover.hh * intersection.first.bounceVector.y)
            mover.dir.invalidate()
        }
    }


    private fun moveOnWall(mover: Thing, obstacle: WallAO, intersection: Pair<Line, Dot>) {
        val moverAngle = mover.dir.angle
        val wallLine = intersection.first
        if ((wallLine.lineAngle12 - moverAngle) < (wallLine.lineAngle21 - moverAngle)) {
            mover.dir.setAngle(wallLine.lineAngle12)
        } else {
            mover.dir.setAngle(wallLine.lineAngle21)
        }
        mover.move()
        mover.dir.setAngle(moverAngle)
    }

    fun bounce(mover: Thing, obstacle: WallAO) {
        val selectedLine = getWallLine(obstacle, mover)
        val originalSpeed = mover.dir.len
        mover.dir.nor()
        val velocityDotProduct = mover.dir.dot(selectedLine.first.bounceVector)
        mover.dir.set(
                mover.dir.x - 2 * velocityDotProduct * selectedLine.first.bounceVector.x,
                mover.dir.y - 2 * velocityDotProduct * selectedLine.first.bounceVector.y)
        mover.dir.scl(originalSpeed)
    }

    private fun getWallLine(obstacle: WallAO, mover: Thing): Pair<Line, Dot> =
            Pair(obstacle.exposedLine, intersectLines(mover, obstacle.exposedLine))

    fun stuck(mover: Thing, obstacle: Thing) {
        if (mover.fast) {
            var cpt = 1
            while (cpt <= rollBackSteps) {
                mover.pos.rollback(obstacle.viscosity(mover) * rollBackPrecision * cpt)
                cpt++
                if (checkCollision(mover, obstacle))
                    mover.pos.rollback(-obstacle.viscosity(mover) * rollBackPrecision * cpt)
                else
                    break
            }
        } else
            mover.pos.rollback(obstacle.viscosity(mover))
    }

    fun contains(thing: Thing, v: Vec2): Boolean {
        when(thing.shape) {
            SquareAO -> return vecInsideSquare(thing, v)
            Circle -> return vecInsideCircle(thing, v)
        }
        return false
    }

    fun dirCenter(other: Thing, me: Thing): Vec2 =
            Vec2.get(other.centerX - me.centerX, other.centerY - me.centerY)

    private fun intersectLines(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float, x4: Float, y4: Float): Dot =
            lineIntersection(x1, y1, x2, y2, x3, y3, x4, y4)

    private fun intersectLines(mover: Thing, line: Line): Dot =
            // the dir * float is there to get a small tolerance
            intersectLines(
                    mover.pCenterX - mover.dir.x * 0.1f, mover.pCenterY - mover.dir.y * 0.1f,
                    mover.centerX + mover.dir.x * 0.1f, mover.centerY + mover.dir.y * 0.1f,
                    line.one.x, line.one.y,
                    line.two.x, line.two.y)

    private fun lineIntersection(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float, x4: Float, y4: Float): Dot {
        val s10_x = x2 - x1
        val s10_y = y2 - y1
        val s32_x = x4 - x3
        val s32_y = y4 - y3

        val denom = s10_x * s32_y - s32_x * s10_y
        if (denom == 0f)
            return Dot.nowhere
        val denomPositive = denom > 0f

        val s02_x = x1 - x3
        val s02_y = y1 - y3
        val s_numer = s10_x * s02_y - s10_y * s02_x
        if ((s_numer < 0) == denomPositive)
            return Dot.nowhere

        val t_numer = s32_x * s02_y - s32_y * s02_x
        if ((t_numer < 0) == denomPositive)
            return Dot.nowhere

        if (((s_numer > denom) == denomPositive) || ((t_numer > denom) == denomPositive))
            return Dot.nowhere

        // Collision detected
        val t = t_numer / denom
        return Dot(x1 + (t * s10_x), y1 + (t * s10_y))
    }

    fun distSq(t1: Thing, t2: Thing): Float {
        val distX = t1.centerX - t2.centerX
        val distY = t1.centerY - t2.centerY
        return (distX * distX) - (distY * distY)
    }

}

package be.julien.seed

import be.julien.donjon.physics.Physics
import be.julien.seed.WallAO.Companion.width
import be.julien.seed.graphics.Drawer

class Vec2 internal constructor(x: Float, y: Float) {

    val PI = 3.1415927f
    val radiansToDegrees = 180f / PI
    val degreesToRadians = PI / 180


    private var x: Float = x
    private var y: Float = y
    private var pX: Float = x
    private var pY: Float = y

    fun move(dir: Vec2, delta: Float) {
        x += dir.x() * delta
        y += dir.y() * delta
    }

    fun move(x: Float, y: Float) {
        this.x += x
        this.y += y
    }

    fun x(): Float {
        return x
    }
    fun y(): Float {
        return y
    }
    fun pX(): Float {
        return pX
    }
    fun pY(): Float {
        return pY
    }

    fun steerLeft(): Vec2 {
        val x = this.x
        this.x = -y
        y = x
        return this
    }

    fun rollback(viscosity: Float): Vec2 {
        val invAlpha = 1.0f - viscosity
        this.x = x * invAlpha + pX * viscosity
        this.y = y * invAlpha + pY * viscosity
        return this
    }

    fun validate() {
        this.pX = this.x
        this.pY = this.y
    }

    fun steerRight(): Vec2 {
        val x = this.x
        this.x = y
        y = -x
        return this
    }

    fun steer(angleDegree: Float, delta: Float): Vec2 {
        rotate(angleDegree * delta)
        return this
    }

    fun set(x: Float, y: Float): Vec2 {
        this.x = x
        this.y = y
        return this
    }

    fun set(other: Vec2): Vec2 {
        set(other.x(), other.y())
        return this
    }

    fun angle(): Float {
        var angle = Math.atan2(y.toDouble(), x.toDouble()).toFloat() * radiansToDegrees
        if (angle < 0) angle += 360f
        return angle
    }

    fun dst(other: Vec2): Float {
        val x_d = other.x - x
        val y_d = other.y - y
        return Math.sqrt((x_d * x_d + y_d * y_d).toDouble()).toFloat()
    }
    fun dst(x: Float, y: Float): Float {
        val x_d = x - this.x
        val y_d = y - this.y
        return Math.sqrt((x_d * x_d + y_d * y_d).toDouble()).toFloat()
    }

    fun nor(): Vec2 {
        val len = len()
        if (len != 0f) {
            x /= len
            y /= len
        }
        return this
    }

    fun rotate(angle: Float): Vec2 {
        rotateRad(angle * degreesToRadians)
        return this
    }
    private fun rotateRad(radians: Float): Vec2 {
        val cos = Math.cos(radians.toDouble()).toFloat()
        val sin = Math.sin(radians.toDouble()).toFloat()

        val newX = this.x * cos - this.y * sin
        val newY = this.x * sin + this.y * cos

        this.x = newX
        this.y = newY

        return this
    }
    private fun setToRandomDirection(): Vec2 {
        set(0f, 1f).rotate(Rnd.float(360f))
        return this
    }

    fun add(other: Vec2): Vec2 {
        x += other.x
        y += other.y
        return this
    }

    fun dot(other: Vec2): Float = x * other.x + y * other.y

    fun scl(f: Float): Vec2 {
        x *= f
        y *= f
        return this
    }

    fun setAngle(f: Float): Vec2 {
        setAngleRad(f * degreesToRadians)
        return this
    }

    fun setAngleRad(radians: Float): Vec2 {
        this.set(len(), 0f)
        this.rotateRad(radians)

        return this
    }

    fun isLeftCloser(other: Vec2): Boolean {
        return isLeftCloser(other.angle())
    }

    fun isLeftCloser(angleOther: Float): Boolean {
        val myAngle = angle()
        val upper = (myAngle + 180f) % 360f
        if (myAngle < 180f)
            return angleOther > myAngle && angleOther < upper
        else
            return angleOther > myAngle || angleOther < upper
    }

    companion object {
        val tmp = Vec2(0f, 0f)
        val zero = Vec2(0f, 0f)

        fun get(x: Float, y: Float): Vec2 {
            return Vec2(x, y)
        }

        fun rnd(): Vec2 {
            return get(0f, 1f).setToRandomDirection()
        }

        fun getRandWorld(drawer: Drawer): Vec2 {
            return getRandWorld(drawer.width(), drawer.height())
        }

        fun getRandWorld(width: Float, height: Float): Vec2 {
            return get(Rnd.float(width), Rnd.float(height))
        }

        fun getRnd(): Vec2 {
            return get(0f, 1f).steer(Rnd.float(360f), 1f)
        }

        fun getRandWorld(excluded: Iterable<Thing>, drawer: Drawer): Vec2 {
            return getRandWorld(excluded, drawer.width(), drawer.height())
        }
        fun getRandWorld(excluded: Iterable<Thing>, width: Float, height: Float): Vec2 {
            (1..50).forEach {
                val v = getRandWorld(width, height)
                excluded.forEach {
                    if (!Physics.contains(it, v))
                        return v
                }
            }
            return getRandWorld(width, height)
        }

        fun get(angle: Float): Vec2 {
            return Vec2(1f, 0f).rotate(angle)
        }
    }

    fun setX(x: Float) {
        this.x = x
    }
    fun setY(y: Float) {
        this.y = y
    }

    fun len(): Float = Math.sqrt((x.toDouble() * x + y * y)).toFloat()

    fun rotate90(i: Int): Vec2 {
        val x = this.x
        if (i >= 0) {
            this.x = -y
            y = x
        } else {
            this.x = y
            y = -x
        }
        return this
    }

    fun bounce(bounceVector: Vec2) {
        x = Math.abs(x)
        y = Math.abs(y)
        x *= bounceVector.x
        y *= bounceVector.y
    }

}
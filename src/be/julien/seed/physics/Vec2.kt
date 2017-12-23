package be.julien.seed.physics

import be.julien.seed.utils.Rnd
import be.julien.seed.basics.Thing
import be.julien.seed.graphics.Drawer

class Vec2 internal constructor(initX: Float, initY: Float) {

    val PI = 3.1415927f
    val radiansToDegrees = 180f / PI
    val degreesToRadians = PI / 180

    private val current = Dot(initX, initY)
    private val previous = Dot(initX, initY)

    val x: Float
        get() = current.x
    val y: Float
        get() = current.y
    val pX: Float
        get() = previous.x
    val pY: Float
        get() = previous.y
    val midX: Float
        get() = (x + pX) / 2f
    val midY: Float
        get() = (y + pY) / 2f
    val angle: Float
        get() {
            var angle = Math.atan2(current.y.toDouble(), current.x.toDouble()).toFloat() * radiansToDegrees
            if (angle < 0) angle += 360f
            return angle
        }
    val dstFromPrevious: Float
        get() {
            return dst(previous.x, previous.y)
        }
    val len: Float
        get() = Math.sqrt((current.x.toDouble() * current.x + current.y * current.y)).toFloat()

    fun move(dir: Vec2, delta: Float) {
        current.x += dir.x * delta
        current.y += dir.y * delta
    }

    fun move(x: Float, y: Float) {
        current.x += x
        current.y += y
    }

    fun steerLeft(): Vec2 {
        val x = current.x
        current.x = -current.y
        current.y = x
        return this
    }

    fun rollback(viscosity: Float): Vec2 {
        val invAlpha = 1.0f - viscosity
        current.x = current.x * invAlpha + previous.x * viscosity
        current.y = current.y * invAlpha + previous.y * viscosity
        return this
    }

    fun invalidate() {
        current.x = previous.x
        current.y = previous.y
    }

    fun validate() {
        previous.x = current.x
        previous.y = current.y
    }

    fun steerRight(): Vec2 {
        val x = current.x
        current.x = current.y
        current.y = -x
        return this
    }

    fun steer(angleDegree: Float, delta: Float): Vec2 {
        rotate(angleDegree * delta)
        return this
    }

    fun set(x: Float, y: Float): Vec2 {
        current.x = x
        current.y = y
        return this
    }

    fun set(other: Vec2): Vec2 {
        set(other.x, other.y)
        return this
    }
    fun dst(other: Vec2): Float {
        val x_d = other.current.x - current.x
        val y_d = other.current.y - current.y
        return Math.sqrt((x_d * x_d + y_d * y_d).toDouble()).toFloat()
    }
    fun dst(x: Float, y: Float): Float {
        val x_d = x - current.x
        val y_d = y - current.y
        return Math.sqrt((x_d * x_d + y_d * y_d).toDouble()).toFloat()
    }

    fun nor(): Vec2 {
        val len = len
        if (len != 0f) {
            current.x /= len
            current.y /= len
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

        val newX = current.x * cos - current.y * sin
        val newY = current.x * sin + current.y * cos

        current.x = newX
        current.y = newY

        return this
    }
    private fun setToRandomDirection(): Vec2 {
        set(0f, 1f).rotate(Rnd.float(360f))
        return this
    }

    fun add(other: Vec2): Vec2 {
        current.x += other.current.x
        current.y += other.current.y
        return this
    }

    fun dot(other: Vec2): Float = current.x * other.current.x + current.y * other.current.y

    fun scl(f: Float): Vec2 {
        current.x *= f
        current.y *= f
        return this
    }

    fun setAngle(f: Float): Vec2 {
        setAngleRad(f * degreesToRadians)
        return this
    }

    fun setAngleRad(radians: Float): Vec2 {
        this.set(len, 0f)
        this.rotateRad(radians)

        return this
    }

    fun isLeftCloser(other: Vec2): Boolean = isLeftCloser(other.angle)

    fun isLeftCloser(angleOther: Float): Boolean {
        val myAngle = angle
        val upper = (myAngle + 180f) % 360f
        return if (myAngle < 180f)
            angleOther > myAngle && angleOther < upper
        else
            angleOther > myAngle || angleOther < upper
    }

    companion object {
        val tmp = Vec2(0f, 0f)
        val zero = Vec2(0f, 0f)

        fun get(x: Float, y: Float): Vec2 = Vec2(x, y)

        fun rnd(): Vec2 = get(0f, 1f).setToRandomDirection()

        fun getRandWorld(drawer: Drawer): Vec2 = getRandWorld(drawer.width(), drawer.height())

        fun getRandWorld(width: Float, height: Float): Vec2 = get(Rnd.float(width), Rnd.float(height))

        fun getRnd(): Vec2 = get(0f, 1f).steer(Rnd.float(360f), 1f)

        fun getRandWorld(excluded: Iterable<Thing>, drawer: Drawer): Vec2 =
                getRandWorld(excluded, drawer.width(), drawer.height())

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

        fun get(angle: Float): Vec2 = Vec2(1f, 0f).rotate(angle)
    }

    fun setX(x: Float) {
        current.x = x
    }
    fun setY(y: Float) {
        current.y = y
    }


    fun rotate90(i: Int): Vec2 {
        val x = current.x
        if (i >= 0) {
            current.x = -current.y
            current.y = x
        } else {
            current.x = current.y
            current.y = -x
        }
        return this
    }

    fun bounce(bounceVector: Vec2) {
        current.x = Math.abs(current.x)
        current.y = Math.abs(current.y)
        current.x *= bounceVector.current.x
        current.y *= bounceVector.current.y
    }

    override fun toString(): String {
        return "Vec2(current=$current, previous=$previous)"
    }

}
package be.julien.seed.basics

import be.julien.donjon.graphics.Drawable
import be.julien.donjon.physics.Physics
import be.julien.seed.physics.shapes.Shape
import be.julien.donjon.graphics.DrawableDim
import be.julien.donjon.things.sensors.Sensor
import be.julien.seed.utils.Rnd
import be.julien.seed.time.Time
import be.julien.seed.graphics.Drawer
import be.julien.seed.physics.Mask
import be.julien.seed.physics.Vec2

abstract class Thing(public val pos: Vec2, public val dir: Vec2, val img: Any) : DrawableDim, Drawable {

    val sensors: MutableCollection<Sensor> = mutableListOf()
    var dead = false

    open fun fast(): Boolean = false

    open fun onWallHit() = Physics::bounce

    /**
     * It means that it will act one more frame after it's dead as the dead flag is probably stat after it has acted and will be only checked next frame, after it has acted again.
     */
    open fun act(delta: Float): Boolean {
        pos.validate()
        move()
        return dead
    }

    open fun move() {
        pos.move(dir, Time.delta)
    }

    open fun draw(drawer: Drawer): Unit {
        drawer.drawAO(this)
    }

    fun steer(angle: Float, delta: Float) {
        dir.rotate(angle * delta)
    }

    open fun die(): Unit {
        dead = true
        sensors.forEach { it.dead = true }
    }

    fun isSensor(b: Thing): Boolean {
        if (b is Sensor)
            return sensors.contains(b)
        return false
    }

    internal fun goAwayFrom(other: Thing, delta: Float) {
        dir.rotate(Physics.goAwayMod(other, this) * 2f)
    }

    internal fun goTowards(other: Thing, delta: Float) {
        dir.rotate(Physics.goAwayMod(other, this) * -2f)
    }

    fun dirCenter(other: Thing): Vec2 {
        return Physics.dirCenter(other, this)
    }

    open fun viscosity(a: Thing): Float {
        return 1f
    }

    open fun collidesWith(thing: Thing) {
    }

    fun centerX(): Float = x() + hw()
    fun centerY(): Float = y() + hh()
    override fun x(): Float = pos.x()
    override fun y(): Float = pos.y()
    override fun angle(): Float = 0f
    override fun img(): Any = img

    abstract fun mask(): Mask
    abstract override fun dimension(): Dimension
    abstract fun shape(): Shape
    fun rndX(): Float {
        return pos.x() + Rnd.float(dimension().width)
    }
    fun rndY(): Float {
        return pos.y() + Rnd.float(dimension().height)
    }


}
package be.julien.seed.basics

import be.julien.seed.graphics.Drawable
import be.julien.seed.physics.Physics
import be.julien.seed.physics.shapes.Shape
import be.julien.seed.utils.Rnd
import be.julien.seed.time.Time
import be.julien.seed.graphics.Drawer
import be.julien.seed.physics.Mask
import be.julien.seed.physics.Vec2
import be.julien.seed.physics.shapes.SquareAO

abstract class Thing(public val pos: Vec2, public val dir: Vec2) : Drawable {

    val sensors: MutableCollection<Sensor> = mutableListOf()
    var dead = false

    override val x: Float
        get() = pos.x
    override val y: Float
        get() = pos.y
    val centerX: Float
        get() = x + hw
    val centerY: Float
        get() = y + hh
    val pCenterX: Float
        get() = pos.pX + hw
    val pCenterY: Float
        get() = pos.pY + hh
    val rndX: Float
        get() = pos.x + Rnd.float(dimension.width)
    val rndY: Float
        get () = pos.y + Rnd.float(dimension.height)
    open val shape: Shape
        get() = SquareAO
    open val mask: Mask
        get() = Mask.Wall
    open val fast: Boolean
        get() = false
    override val angle: Float
        get() = dir.angle

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

    open fun draw(drawer: Drawer) {
        drawer.drawAO(this)
    }

    fun steer(angle: Float, delta: Float) {
        dir.rotate(angle * delta)
    }

    open fun die() {
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

    fun dirCenter(other: Thing): Vec2 = Physics.dirCenter(other, this)

    open fun viscosity(a: Thing): Float = 1f

    open fun collidesWith(thing: Thing) {
    }

    fun setCenter(x: Float, y: Float) {
        pos.set(x - hw, y - hh)
    }

}
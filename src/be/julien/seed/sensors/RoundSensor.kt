package be.julien.seed.sensors

import be.julien.seed.physics.shapes.Circle
import be.julien.seed.physics.shapes.Shape
import be.julien.donjon.things.sensors.Sensor
import be.julien.seed.Dimension
import be.julien.seed.Thing
import be.julien.seed.graphics.Drawer

class RoundSensor private constructor(anchor: Thing, radius: Float): Sensor(anchor) {
    override fun tr(): () -> Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    val dim = Dimension.get(radius, radius)
    override fun shape(): Shape = Circle
    override fun dimension(): Dimension = dim

    override fun act(delta: Float): Boolean {
        pos.set(anchor.centerX() - dim.halfWidth, anchor.centerY() - dim.halfHeight)
        return dead
    }

    override fun draw(drawer: Drawer) {
//        drawer.color(Color.GRAY)
//        super.draw(drawer)
    }

    companion object {
        fun get(anchor: Thing, radius: Float): RoundSensor {
            return RoundSensor(anchor, radius)
        }
    }

}
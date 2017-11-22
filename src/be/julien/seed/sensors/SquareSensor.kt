package be.julien.seed.sensors

import be.julien.seed.physics.shapes.Shape
import be.julien.seed.physics.shapes.SquareAO
import be.julien.donjon.things.sensors.Sensor
import be.julien.seed.Dimension
import be.julien.seed.Vec2
import be.julien.seed.Thing
import be.julien.seed.graphics.Color
import be.julien.seed.graphics.Drawer

class SquareSensor internal constructor(anchor: Thing, sensorLength: Float, val offsetAngle: Float, width: Float):
        Sensor(anchor) {
    override fun img(): () -> Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    internal val offset = Vec2.get(sensorLength, 0f)
    internal val dim = Dimension.get(width, width)

    override fun shape(): Shape = SquareAO
    override fun dimension(): Dimension = dim

    override fun act(delta: Float): Boolean {
        offset.setAngle(offsetAngle + anchor.angle())
        pos.set(anchor.x() + anchor.hw() + offset.x() - hw(), anchor.y() + anchor.hh() + offset.y() - hh())
        return dead
    }

    override fun draw(drawer: Drawer) {
        drawer.color(Color.WHITE)
        super.draw(drawer)
    }

    companion object {
        fun get(anchor: Thing, sensorLength: Float, offsetAngle: Float, width: Float): SquareSensor {
            return SquareSensor(anchor, sensorLength, offsetAngle, width)
        }
    }

}
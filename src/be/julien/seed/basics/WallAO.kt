package be.julien.seed.basics

import be.julien.seed.physics.Physics
import be.julien.seed.physics.shapes.Shape
import be.julien.seed.physics.shapes.SquareAO
import be.julien.seed.graphics.Drawer
import be.julien.seed.physics.Line
import be.julien.seed.physics.Mask
import be.julien.seed.physics.Vec2

class WallAO(x: Float, y: Float, width: Float, height: Float,
             img: Any,
             val dim: Dimension = Dimension.get(width, height)): Thing(Vec2.get(x, y), Vec2.get(0f, 0f), img) {

    val lines = arrayOf(
        // bottom
        Line(x + width, y, x, y, Vec2(0f, -1f)),
        // right
        Line(x + width, y, x + width, y + height, Vec2(1f, 0f)),
        // top
        Line(x + width, y + height, x, y + height, Vec2(0f, 1f)),
        // left
        Line(x, y + height, x, y, Vec2(-1f, 0f)))

    private val normals = arrayOf(
            Normal(
                    x, y,                                    // bottom left
                    x + width, y,                   // bottom right
                    x + width / 2, y + height / 2),
            Normal(
                    x + width, y + height,     // top right
                    x, y + height,                  // top left
                    x + dim.halfWidth, y + dim.halfHeight),
            Normal(
                    x, y + height,                 // top left
                    x, y,                                   // bottom left
                    x + dim.halfWidth, y + dim.halfHeight),
            Normal(
                    x + width, y,                    // bottom right
                    x + width, y + height,  // top right
                    x + dim.halfWidth, y + dim.halfHeight)
    )

    override fun shape(): Shape = SquareAO
    override fun dimension(): Dimension = dim
    override fun mask(): Mask = Mask.Wall

    fun normal(t: Thing): Normal {
        normals.forEach {
            if (it.within(t.centerX(), t.centerY()))
                return it
        }
        return Normal.dummy
    }

    override fun debug(drawer: Drawer) {
        super.debug(drawer)
        normals.forEach {
            drawer.color(1f)
            drawer.draw(img(), centerX(), centerY(), 0f, 0f, 20f, 0.2f, it.angle)
        }
    }

    companion object {
        val width = 2f
    }

}

class Normal(private val x1: Float, private val y1: Float, private val x2: Float, private val y2: Float, private val centerX: Float, private val centerY: Float) {
    val angle: Float = Physics.angle(x1, y1, x2, y2) + 90f
    val vec = Vec2.get(angle)
    fun within(x: Float, y: Float): Boolean = Physics.linesIntersect(x1, y1, x2, y2, centerX, centerY, x, y)

    companion object {
        val dummy = Normal(0f, 0f, 0f, 0f, 0f, 0f)
    }
}
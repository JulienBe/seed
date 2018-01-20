package be.julien.seed.ui

import be.julien.seed.basics.Dimension
import be.julien.seed.graphics.Drawable
import be.julien.seed.graphics.Drawer
import be.julien.seed.physics.Vec2

abstract class FontPiece(col: Int, row: Int, parent: Vec2): Drawable {
    override val x: Float
        get() = anchor.x
    override val y: Float
        get() = anchor.y
    override val angle: Float
        get() = 0f
    override val dimension: Dimension
        get() = dim

    val anchor: Vec2 = Vec2(parent.x + col * width, parent.y + row * width)

    fun draw(drawer: Drawer) {
        drawer.drawAO(this)
    }

    companion object {
        val width = 1f
        val dim = Dimension.get(width, width)
    }

}
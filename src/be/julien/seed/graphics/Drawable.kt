package be.julien.seed.graphics

import be.julien.seed.basics.Dimension

interface Drawable {
    val hw: Float
        get() = dimension.halfWidth
    val w: Float
        get() = dimension.width
    val hh: Float
        get() = dimension.halfHeight
    val h: Float
        get() = dimension.height

    val x: Float
    val y: Float
    val angle: Float
    val dimension: Dimension
    val img: Any

    fun debug(drawer: Drawer) {
    }
}
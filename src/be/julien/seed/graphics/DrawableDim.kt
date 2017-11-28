package be.julien.seed.graphics

import be.julien.seed.basics.Dimension

interface DrawableDim : Drawable {
    override fun hw(): Float = dimension().halfWidth
    override fun w(): Float = dimension().width
    override fun hh(): Float = dimension().halfHeight
    override fun h(): Float = dimension().height
    fun dimension(): Dimension
}
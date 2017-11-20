package be.julien.donjon.graphics

import be.julien.seed.Dimension

interface DrawableDim : Drawable {
    override fun hw(): Float {
        return dimension().halfWidth
    }
    override fun w(): Float {
        return dimension().width
    }
    override fun hh(): Float {
        return dimension().halfHeight
    }
    override fun h(): Float {
        return dimension().height
    }
    fun dimension(): Dimension
}
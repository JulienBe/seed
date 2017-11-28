package be.julien.seed.basics

class Dimension private constructor(val width: Float, val height: Float) {

    val halfWidth = width / 2f
    val halfHeight = height / 2f

    companion object {
        fun get(width: Float, height: Float): Dimension = Dimension(width, height)
    }
}
package be.julien.seed.basics

import be.julien.seed.physics.Line
import be.julien.seed.physics.Vec2

abstract class WallAO(x: Float, y: Float, width: Float, height: Float,
                      exposedSide: WallSide,
                      val dim: Dimension = Dimension.get(width, height),
                      val exposedLine: Line = getExposedLine(exposedSide, x, y, width, height)
             ): Thing(Vec2.get(x, y), Vec2.get(0f, 0f)) {

    override val angle: Float
        get() = 0f
    override val dimension: Dimension
        get() = dim

    companion object {
        val width = 2f
    }
}

fun getExposedLine(exposedSide: WallSide, x: Float, y: Float, width: Float, height: Float): Line {
    if (exposedSide == WallSide.right)
        return Line(x + width, y, x + width, y + height, Vec2(1f, 0f))
    if (exposedSide == WallSide.top)
        return Line(x + width, y + height, x, y + height, Vec2(0f, 1f))
    if (exposedSide == WallSide.left)
        return Line(x, y + height, x, y, Vec2(-1f, 0f))
    if (exposedSide == WallSide.bottom)
        return Line(x + width, y, x, y, Vec2(0f, -1f))
    throw Throwable("Line not found")
}

enum class WallSide {
    right, top, left, bottom
}
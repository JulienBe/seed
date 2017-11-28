package be.julien.seed.physics

class Line(val one: Dot, val two: Dot, val bounceVector: Vec2) {
    constructor(x1: Float, y1: Float, x2: Float, y2: Float, bounceVector: Vec2) : this(Dot(x1, y1), Dot(x2, y2), bounceVector)
}
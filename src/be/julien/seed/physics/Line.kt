package be.julien.seed.physics

class Line(val one: Dot, val two: Dot, val bounceVector: Vec2,
           val bounceAngle: Float = bounceVector.angle,
           val lineAngle12: Float = Physics.angle(one, two),
           val lineAngle21: Float = Physics.angle(two, one)) {
    constructor(x1: Float, y1: Float, x2: Float, y2: Float, bounceVector: Vec2) : this(Dot(x1, y1), Dot(x2, y2), bounceVector)

    override fun toString(): String {
        return "Line(one=$one, two=$two, bounceVector=$bounceVector, bounceAngle=$bounceAngle, lineAngle12=$lineAngle12, lineAngle21=$lineAngle21)"
    }

}
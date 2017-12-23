package be.julien.seed.physics

class Dot(var x: Float, var y: Float) {
    companion object {
        val nowhere = Dot(-999f, -999f)
    }

    override fun toString(): String {
        return "Dot(x=$x, y=$y)"
    }

}
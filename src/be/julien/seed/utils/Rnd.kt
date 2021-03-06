package be.julien.seed.utils

import java.util.*

object Rnd {
    internal val rnd = Random()

    fun int(max: Int): Int {
        if (max <= 0) return 1
        return rnd.nextInt(max)
    }

    fun float(mult: Float = 1f): Float = rnd.nextFloat() * mult
    fun float(): Float = rnd.nextFloat()
    fun bool(): Boolean = rnd.nextBoolean()
    fun gauss(): Float = rnd.nextGaussian().toFloat()
    fun gauss(spread: Float): Float = gauss() * spread
}
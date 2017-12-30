package be.julien.seed.basics

import be.julien.seed.physics.Vec2
import be.julien.seed.physics.Mask

abstract class Sensor(var anchor: Thing) : Thing(Vec2.get(0f, 0f), Vec2.get(0f, 0f)) {
    val colliders: MutableCollection<Thing> = mutableListOf()

    override val mask: Mask
        get() = Mask.Sensor

    fun checked() {
        colliders.clear()
    }

    fun containersEnergy(): Boolean {
//        colliders.forEach { if (it is Energy) return true }
        return false
    }

    override fun collidesWith(thing: Thing) {
        colliders.add(thing)
    }

    fun getEnergy(): Thing? {
//        colliders.forEach { if (it is Energy) return it }
        return null
    }
}
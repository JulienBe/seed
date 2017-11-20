package be.julien.donjon.things.sensors

import be.julien.seed.Vec2
import be.julien.seed.Thing
import be.julien.seed.physics.Mask

abstract class Sensor(var anchor: Thing) : Thing(Vec2.get(0f, 0f), Vec2.get(0f, 0f)) {
    internal val colliders = mutableListOf<Thing>()

    override fun mask(): Mask = Mask.Sensor

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
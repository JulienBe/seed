package be.julien.seed.sensors

import be.julien.seed.physics.Vec2
import be.julien.seed.basics.Thing
import be.julien.seed.physics.Mask

abstract class Sensor(var anchor: Thing, img: Any) : Thing(Vec2.get(0f, 0f), Vec2.get(0f, 0f), img) {
    val colliders: MutableCollection<Thing> = mutableListOf()

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
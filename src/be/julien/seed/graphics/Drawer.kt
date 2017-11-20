package be.julien.seed.graphics

import be.julien.seed.Thing

interface Drawer {
    fun color(color: Float)
    fun drawSquare(x: Float, y: Float, originX: Float, originY: Float, width: Float, height: Float, angle: Float) {}
    fun drawAO(thing: Thing)
}
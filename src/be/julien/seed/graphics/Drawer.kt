package be.julien.seed.graphics

import be.julien.donjon.graphics.Drawable

interface Drawer {
    fun color(color: Float)
    fun draw(img: Any, x: Float, y: Float, pivotX: Float, pivotY: Float, width: Float, height: Float, angle: Float)
    fun drawAO(d: Drawable)
    fun drawNAO(d: Drawable)
    fun drawAO(img: Any, x: Float, y: Float, width: Float, height: Float)
    fun width(): Float
    fun height(): Float
}
package be.julien.donjon.graphics

import be.julien.seed.graphics.Drawer

interface Drawable {
    fun hw(): Float
    fun w(): Float
    fun hh(): Float
    fun h(): Float
    fun x(): Float
    fun y(): Float
    fun tr(): () -> Any
    fun angle(): Float
    fun debug(drawer: Drawer) {
    }
}
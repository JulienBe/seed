package be.julien.seed.graphics

interface Drawable {
    fun hw(): Float
    fun w(): Float
    fun hh(): Float
    fun h(): Float
    fun x(): Float
    fun y(): Float
    fun img(): Any
    fun angle(): Float
    fun debug(drawer: Drawer) {
    }
}
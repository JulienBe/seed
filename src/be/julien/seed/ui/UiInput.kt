package be.julien.seed.ui

import be.julien.seed.physics.Vec2

interface UiInput {

    fun mousePos(): Vec2
    fun justClicked(): Boolean

}
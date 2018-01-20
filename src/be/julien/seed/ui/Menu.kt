package be.julien.seed.ui

import be.julien.seed.graphics.Drawer

class Menu(val buttons: Array<Button>) {
    fun act() {

    }

    fun draw(drawer: Drawer) {
        buttons.forEach {
            it.draw(drawer)
        }
    }
}
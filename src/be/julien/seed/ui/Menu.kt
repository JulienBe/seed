package be.julien.seed.ui

import be.julien.seed.graphics.Drawer

class Menu(val buttons: Array<Button>) {
    fun act(uiInput: UiInput) {
        buttons.forEach {
            it.act(uiInput)
        }
    }

    fun draw(drawer: Drawer) {
        buttons.forEach {
            it.draw(drawer)
        }
    }
}
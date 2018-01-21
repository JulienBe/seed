package be.julien.seed.ui

import be.julien.seed.basics.Dimension
import be.julien.seed.graphics.Drawable
import be.julien.seed.graphics.Drawer
import be.julien.seed.physics.Physics
import be.julien.seed.physics.Vec2

class Button(val pos: Vec2, strs: Array<String>, fontPieceConst: (col: Int, row: Int, parent: Vec2) -> FontPiece): Drawable {
    override val x: Float
        get() = pos.x
    override val y: Float
        get() = pos.y - dim.height
    override val angle: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val dimension: Dimension
        get() = dim
    override val img: Any
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    val pieces: Array<FontPiece> = getPieces(strs, fontPieceConst)
    val col: Int = strs.maxBy { it.length }?.length ?: 0
    val row: Int = strs.size
    val dim = Dimension.get(col * FontPiece.width, row * FontPiece.width)

    fun getPieces(strs: Array<String>, fontPieceConst: (col: Int, row: Int, parent: Vec2) -> FontPiece): Array<FontPiece> {
        val array = mutableListOf<FontPiece>()
        var currentRow = 0
        var currentCol = 0
        strs.forEach {
            str -> str.forEach {
                c -> if (c.equals('*'))
                    array.add(fontPieceConst(currentCol, row - currentRow, pos))
                currentCol += 1
            }
            currentCol = 0
            currentRow += 1
        }
        return array.toTypedArray()
    }

    fun draw(drawer: Drawer) {
        pieces.forEach { it.draw(drawer) }
    }

    fun act(uiInput: UiInput) {
        if (uiInput.justClicked() && isClicked(uiInput.mousePos()))
            clicked()
    }

    private fun clicked() {
        println("clicked")
    }

    private fun isClicked(mousePos: Vec2): Boolean {
        return Physics.vecInsideSquare(this, mousePos)
    }

}
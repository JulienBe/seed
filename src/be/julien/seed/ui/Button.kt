package be.julien.seed.ui

import be.julien.seed.graphics.Drawer
import be.julien.seed.physics.Vec2

class Button(val pos: Vec2, strs: Array<String>, fontPieceConst: (col: Int, row: Int, parent: Vec2) -> FontPiece) {

    val pieces: Array<FontPiece> = getPieces(strs, fontPieceConst)
    val col: Int = strs.maxBy { it.length }?.length ?: 0
    val row: Int = strs.size

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

}
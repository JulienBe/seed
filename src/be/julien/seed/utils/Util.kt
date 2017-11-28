package be.julien.seed.utils

object Util {

    fun out(string: String, logger: SeedLogger) {
        logger.log(string)
    }

    fun lineToRow(line: Int, columns: Int): Int = line / columns

    fun lineToCol(line: Int, columns: Int): Int = line % columns

}
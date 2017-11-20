package be.julien.seed

object Util {

    fun out(string: String, logger: SeedLogger) {
        logger.log(string)
    }

    fun lineToRow(line: Int, columns: Int): Int {
        return line / columns
    }

    fun lineToCol(line: Int, columns: Int): Int {
        return line % columns
    }

    fun err(s: String, logger: SeedLogger) {
        logger.error(s)
    }

}
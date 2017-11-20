package be.julien.seed

object SeedLoggerImpl : SeedLogger {
    override fun error(s: String) {
        System.err.println(s)
    }

    override fun log(s: String) {
        println(s)
    }
}
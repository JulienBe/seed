package be.julien.seed.time

class PeriodicTimer(interval: Float, val callback: Callback) {
    val timeInt = TimeInt.get(0, interval, 1, callback)

    init {
        callback.callbackValue = 1
    }

    fun act() {
        timeInt.act()
        if (callback.triggered)
            timeInt.reset()
    }
}
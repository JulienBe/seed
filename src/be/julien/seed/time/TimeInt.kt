package be.julien.seed.time

class TimeInt private constructor(var value: Int, private var interval: Float = 1f, internal var increment: Int, var callback: Callback?) {

    private var nextTrigger: Float = Time.time + interval
    private val initialVal = value

    fun act() {
        if (nextTrigger < Time.time) {
            nextTrigger = Time.time + interval
            step()
        }
    }

    fun step() {
        step(1)
    }

    fun step(steps: Int) {
        value += increment * steps
        callback?.check(this)
    }

    fun setCallback(callbackValue: Int, method: () -> Unit) {
        callback = Callback.get(callbackValue, method)
    }

    fun add(i: Int) {
        value += i
    }

    fun reset() {
        value = initialVal
        nextTrigger = Time.time + interval
        callback?.reset()
    }

    fun noCallback() {}

    companion object {
        fun get(value: Int, interval: Float, increment: Int): TimeInt {
            return TimeInt(value, interval, increment, null)
        }

        fun get(value: Int, interval: Float, increment: Int, callback: Callback): TimeInt {
            return TimeInt(value, interval, increment, callback)
        }
    }
}

class Callback(var callbackValue: Int, var callback: () -> Unit) {
    internal var triggered = false
    fun check(timeInt: TimeInt) {
        if (!triggered) {
            if (timeInt.increment > 0f && timeInt.value >= callbackValue)
                activate()
            else if (timeInt.increment < 0f && timeInt.value <= callbackValue)
                activate()
        }
    }

    private fun activate() {
        triggered = true
        callback.invoke()
    }

    fun reset() {
        triggered = false
    }

    companion object {
        fun get(callbackValue: Int, method: () -> Unit): Callback {
            return Callback(callbackValue, method)
        }
    }

}


package uk.co.waterrower.waterrowerdata.sample.util

interface Cancellable {

    fun cancel()
    fun isCancelled(): Boolean

    companion object {

        fun cancellable(f: () -> Unit) = object : Cancellable {

            private var cancelled = false

            override fun cancel() {
                cancelled = true
                f()
            }

            override fun isCancelled(): Boolean {
                return cancelled
            }
        }
    }
}

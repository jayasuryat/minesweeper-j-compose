package com.jayasuryat.util

public class TrackedLazyCollector<T> {

    private val delegates: MutableList<Lazy<T>> = mutableListOf()

    public fun trackedLazy(logic: () -> T): Lazy<T> {
        val delegate = lazy { logic() }
        synchronized(delegates) { delegates.add(delegate) }
        return delegate
    }

    public fun onEachInitialized(logic: (T) -> Unit) {

        synchronized(delegates) {
            delegates.forEach { if (it.isInitialized()) logic(it.value) }
        }
    }
}
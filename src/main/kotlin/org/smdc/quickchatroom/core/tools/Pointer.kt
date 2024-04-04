package org.smdc.quickchatroom.core.tools

open class Pointer<T>(protected var value: T?) {
    constructor() : this(null)

    fun get(): T? {
        return value
    }

    fun set(v: T?) {
        value = v
    }
}
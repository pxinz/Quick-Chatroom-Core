package org.smdc.quickchatroom.core.api

import org.smdc.quickchatroom.core.connection.socket.DataPackage
import org.smdc.quickchatroom.core.tools.Pointer

open class ReturnMap : HashMap<Int, ((DataPackage) -> Any?)>() {
    open fun getLock(rid: Int): () -> DataPackage? {
        val lock = Object()
        val pointer = Pointer<DataPackage>()

        this[rid] = { pkg ->
            pointer.set(pkg)
            synchronized(lock) {
                lock.notifyAll()
            }
        }

        return {
            synchronized(lock) {
                try {
                    lock.wait()
                    return@synchronized pointer.get()
                } catch (_: InterruptedException) {
                    return@synchronized null
                }
            }
        }
    }

    open fun waitForPackage(rid: Int): DataPackage? {
        return getLock(rid).invoke()
    }

    open fun putPackage(rid: Int, pack: DataPackage): Boolean {
        if (this.containsKey(rid)) {
            this[rid]?.invoke(pack)
            this.remove(rid)
            return true
        }
        return false
    }

    open fun putPackage(pack: DataPackage): Boolean {
        return putPackage(pack.rId, pack)
    }
}
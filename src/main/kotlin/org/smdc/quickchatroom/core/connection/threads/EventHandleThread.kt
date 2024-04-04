package org.smdc.quickchatroom.core.connection.threads

import org.smdc.quickchatroom.core.event.EventQueue
import org.smdc.quickchatroom.core.tools.PausableThread

open class EventHandleThread(protected open val queue: EventQueue) : PausableThread() {
    override fun beforeLoop() {
        queue.setFuncOnAdd { resumeT() }
    }

    override fun loopItem() {
        queue.handleAll()
        System.gc()
    }

    override fun afterLoop() {
        queue.setFuncOnAdd(null)
    }
}
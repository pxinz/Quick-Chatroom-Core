package org.smdc.quickchatroom.core.event

/**
 * 事件队列中的事件
 */
abstract class Event {
    /**
     * 事件处理状态
     */
    open var done: Boolean = false

    /**
     * 更改事件处理状态, 若为false则改为true, 若为true则改为false
     */
    open fun handle() {
        done = !done
    }

    /**
     * 更改事件处理状态为实参h
     *
     * @param h 事件处理状态
     */
    open fun handle(h: Boolean) {
        done = h
    }
}
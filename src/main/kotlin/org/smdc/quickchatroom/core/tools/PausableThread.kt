package org.smdc.quickchatroom.core.tools

import kotlin.concurrent.Volatile

abstract class PausableThread : Thread() {
    /**
     * 用于暂停和恢复线程的对象锁
     */
    protected open val lock = Object()

    /**
     * 标记当前线程是否暂停
     */
    @Volatile
    protected open var paused = false

    protected open fun beforeLoop() {}

    protected abstract fun loopItem()

    protected open fun afterLoop() {}

    /**
     * 暂停线程
     */
    open fun pauseT() {
        paused = true
    }

    /**
     * 恢复线程正常执行
     */
    open fun resumeT() {
        paused = false
        synchronized(lock) {
            lock.notifyAll()
        }
    }

    override fun run() {
        // 执行循环前方法
        beforeLoop()
        // 如果线程未被中断, 则一直循环执行操作
        while (!isInterrupted) {
            if (!paused) {
                // 线程解除暂停: 执行循环方法后再次暂停
                loopItem()
                pauseT()
            } else {
                synchronized(lock) {
                    try {
                        // 当前线程被暂停, 等待通知
                        lock.wait()
                    } catch (e: InterruptedException) {
                        // 如果线程被打断, 则中断线程
                        interrupt()
                    }
                }
            }
        }
        // 执行循环后方法
        afterLoop()
    }

    open fun isPaused(): Boolean {
        return paused
    }
}


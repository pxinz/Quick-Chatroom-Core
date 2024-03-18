package org.smdc.quickchatroom.core.tools

import kotlin.concurrent.Volatile

/**
 * 可暂停的线程(暂停在循环方法loopItem执行完毕后)
 */
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

    /**
     * 在循环前执行的方法
     */
    protected open fun beforeLoop() {}

    /**
     * 在循环中执行的方法
     */
    protected abstract fun loopItem()

    /**
     * 在循环后执行的方法
     */
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

    /**
     * 获取是否被暂停
     *
     * @return 是否被暂停
     */
    open fun isPaused(): Boolean {
        return paused
    }
}


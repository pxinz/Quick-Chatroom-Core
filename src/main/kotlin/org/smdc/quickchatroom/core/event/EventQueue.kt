package org.smdc.quickchatroom.core.event

import org.smdc.quickchatroom.core.event.handler.EventHandleMethodsMap
import java.lang.reflect.InvocationTargetException
import java.util.*

/**
 * 事件队列
 */
open class EventQueue : LinkedList<Event>() {
    /**
     * 事件处理方法
     */
    open val handleMethodsMap = EventHandleMethodsMap()

    /**
     * 添加事件时执行的方法
     */
    protected open var onAdd: ((Event) -> Any)? = null

    /**
     * 处理队列中最前的事件
     * @exception InvocationTargetException 执行方法中可能出现的错误
     * @exception IllegalAccessException 执行方法中可能出现的错误
     *
     * @see EventHandleMethodsMap.handle
     */
    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    fun handleOne() {
        handleMethodsMap.handle(pop())
    }

    /**
     * 处理队列中所有事件
     * @exception InvocationTargetException 执行方法中可能出现的错误
     * @exception IllegalAccessException 执行方法中可能出现的错误
     *
     * @return 本次处理的事件数
     *
     * @see EventHandleMethodsMap.handle
     */
    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    fun handleAll(): Int {
        var count = 0
        while (isNotEmpty()) {
            handleOne()
            count++
        }
        return count
    }

    override fun add(element: Event): Boolean {
        try {
            onAdd?.invoke(element)
        } catch (ignored: InvocationTargetException) {
        } catch (ignored: IllegalAccessException) {
        }

        return super.add(element)
    }

    /**
     * 设置添加事件时执行的方法
     */
    fun setFuncOnAdd(onAdd: ((Event) -> Any)?) {
        this.onAdd = onAdd
    }
}

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

    private var onAdd: ((Event) -> Any)? = null

    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    fun handle(event: Event) {
        handleMethodsMap[event.javaClass]?.forEach { method ->
            if (!event.done) {
                method.invoke(event)
            }
        }
    }

    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    fun handleOne() {
        handle(pop())
    }

    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    fun handleAll() {
        while (isNotEmpty()) {
            handleOne()
        }
    }

    override fun add(element: Event): Boolean {
        try {
            onAdd?.invoke(element)
        } catch (ignored: InvocationTargetException) {
        } catch (ignored: IllegalAccessException) {
        }

        return super.add(element)
    }

    fun setOnAdd(onAdd: ((Event) -> Any)?) {
        this.onAdd = onAdd
    }
}

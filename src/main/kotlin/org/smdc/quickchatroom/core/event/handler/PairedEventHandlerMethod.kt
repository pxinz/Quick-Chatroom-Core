package org.smdc.quickchatroom.core.event.handler

import org.smdc.quickchatroom.core.event.Event
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Modifier

/**
 * 将事件处理器与事件处理方法配对
 */
open class PairedEventHandlerMethod(method: Method, handler: EventHandler?) {
    companion object {
        /**
         * 获取一个Method对应的事件类型(安全版)
         * @param method method
         *
         * @return 事件类型(若method不是事件处理方法，则返回null)
         */
        @Suppress("UNCHECKED_CAST")
        fun safeGetHandlerMethodType(method: Method): Class<out Event>? {
            val types = method.parameterTypes
            if (!method.isAnnotationPresent(EventHandlerMethod::class.java) ||
                types.isEmpty() ||
                !Event::class.java.isAssignableFrom(types[0])
            ) return null
            return types[0] as Class<out Event>
        }

        /**
         * 获取一个Method对应的事件类型
         * @param method method
         *
         * @return 事件类型
         */
        @Suppress("UNCHECKED_CAST")
        fun getHandlerMethodType(method: Method): Class<out Event> {
            return method.parameterTypes[0] as Class<out Event>
        }

        /**
         * 获取一个Method的EventHandlerMethod注解
         * @param method method
         *
         * @return EventHandlerMethod注解(若注解不存在，则返回null)
         */
        fun getHandlerMethodAnnotation(method: Method): EventHandlerMethod? {
            for (annotation in method.annotations) {
                if (annotation is EventHandlerMethod) {
                    return annotation
                }
            }
            return null
        }
    }

    /**
     * 事件处理方法
     */
    private val method: Method

    /**
     * 执行处理方法的对象
     */
    private val handler: EventHandler?

    /**
     * 事件处理类型
     */
    private val type: Class<out Event>

    open fun getMethod(): Method {
        return method
    }

    open fun getHandler(): EventHandler? {
        return handler
    }

    open fun getType(): Class<out Event> {
        return type
    }

    init {
        this.method = method
        this.handler = handler


        val type = safeGetHandlerMethodType(method)
        if (type == null) {
            throw IllegalArgumentException("传入的method不是事件处理方法")
        } else {
            this.type = type
        }

        if (handler == null && !Modifier.isStatic(method.modifiers)){
            throw IllegalArgumentException("无执行者不可使用于非静态方法")
        }
    }

    /**
     * 处理事件
     *
     * @param event 事件对象
     * @throws InvocationTargetException 执行反射函数时的出错
     * @throws IllegalAccessException    执行反射函数时的出错
     */
    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    open fun invoke(event: Event?) {
        method.invoke(handler, event)
    }
}
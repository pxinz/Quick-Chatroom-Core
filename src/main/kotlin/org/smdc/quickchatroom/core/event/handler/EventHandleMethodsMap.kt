package org.smdc.quickchatroom.core.event.handler

import org.smdc.quickchatroom.core.event.Event
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * 事件与事件处理方法的对应
 */
open class EventHandleMethodsMap : HashMap<Class<out Event>, ArrayList<PairedEventHandlerMethod>>() {
    /**
     * 注册一个事件处理方法
     * @param method 事件处理方法(带有EventHandlerMethod注解)
     * @param obj 事件处理器(若为静态方法则传入null)
     */
    open fun registerHandleMethod(method: Method, obj: EventHandler?) {
        val pairedMethod = PairedEventHandlerMethod(method, obj)
        if (pairedMethod.getType() !in keys) {
            this[pairedMethod.getType()] = ArrayList()
        }
        this[pairedMethod.getType()]?.add(pairedMethod)
    }

    /**
     * 注销一个事件处理方法(受事件处理器制约)
     * @param method 事件处理方法(带有EventHandlerMethod注解)
     * @param obj 事件处理器(若为静态方法则传入null)
     */
    open fun deregisterHandleMethod(method: Method, obj: EventHandler?) {
        val methods = this[PairedEventHandlerMethod.safeGetHandlerMethodType(method)]
        methods?.forEach { pairedMethod ->
            if (pairedMethod.getMethod() == method && pairedMethod.getHandler() == obj) {
                methods.remove(pairedMethod)
            }
        }
    }

    /**
     * 注销一个事件处理方法(不受事件处理器制约)
     * @param method 事件处理方法(带有EventHandlerMethod注解)
     */
    open fun deregisterHandleMethod(method: Method) {
        val methods = this[PairedEventHandlerMethod.safeGetHandlerMethodType(method)]
        methods?.forEach { pairedMethod ->
            if (pairedMethod.getMethod() == method) {
                methods.remove(pairedMethod)
            }
        }
    }

    /**
     * 注册一个事件处理类(等效于注册类中所有带有EventHandlerMethod注解的方法)
     * @param handler 事件处理类
     * @param obj 事件处理器(若全为静态方法则传入null)
     */
    open fun <T : EventHandler> registerHandler(handler: Class<T>, obj: T?) {
        val methods = handler.methods
        methods.sortWith { a, b ->
            val annotationA = PairedEventHandlerMethod.getHandlerMethodAnnotation(a)
            val annotationB = PairedEventHandlerMethod.getHandlerMethodAnnotation(b)
            if (annotationA == null || annotationB == null) {
                return@sortWith -1
            }
            return@sortWith annotationA.ordinal.compareTo(annotationB.ordinal)
        }
        methods.forEach { method ->
            try {
                registerHandleMethod(method, obj)
            } catch (ignored: IllegalArgumentException) {
            }
        }
    }

    /**
     * 注销一个事件处理类(等效于注册类中所有带有EventHandlerMethod注解的方法)(受事件处理器制约)
     * @param handler 事件处理类
     * @param obj 事件处理器(若全为静态方法则传入null)
     */
    open fun deregisterHandler(handler: Class<out EventHandler>, obj: EventHandler?) {
        handler.methods.forEach { method ->
            deregisterHandleMethod(method, obj)
        }
    }

    /**
     * 注销一个事件处理类(等效于注册类中所有带有EventHandlerMethod注解的方法)(不受事件处理器制约)
     * @param handler 事件处理类
     */
    open fun deregisterHandler(handler: Class<out EventHandler>) {
        handler.methods.forEach { method ->
            deregisterHandleMethod(method)
        }
    }

    /**
     * 处理一个事件
     * @exception InvocationTargetException 执行方法中可能出现的错误
     * @exception IllegalAccessException 执行方法中可能出现的错误
     */
    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    fun handle(event: Event) {
        this[event.javaClass]?.forEach { method ->
            if (!event.done) {
                method.invoke(event)
            }
        }
    }
}
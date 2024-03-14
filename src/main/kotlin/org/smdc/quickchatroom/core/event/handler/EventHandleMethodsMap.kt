package org.smdc.quickchatroom.core.event.handler

import org.smdc.quickchatroom.core.event.Event
import java.lang.reflect.Method

open class EventHandleMethodsMap : HashMap<Class<out Event>, ArrayList<PairedEventHandlerMethod>>() {
    open fun registerHandleMethod(method: Method, obj: EventHandler?) {
        val pairedMethod = PairedEventHandlerMethod(method, obj)
        if (pairedMethod.getType() !in keys) {
            this[pairedMethod.getType()] = ArrayList()
        }
        this[pairedMethod.getType()]?.add(pairedMethod)
    }

    open fun deregisterHandleMethod(method: Method, obj: EventHandler?) {
        val methods = this[PairedEventHandlerMethod.safeGetHandlerMethodType(method)]
        methods?.forEach { pairedMethod ->
            if (pairedMethod.getMethod() == method && pairedMethod.getHandler() == obj) {
                methods.remove(pairedMethod)
            }
        }
    }

    open fun deregisterHandleMethod(method: Method) {
        val methods = this[PairedEventHandlerMethod.safeGetHandlerMethodType(method)]
        methods?.forEach { pairedMethod ->
            if (pairedMethod.getMethod() == method) {
                methods.remove(pairedMethod)
            }
        }
    }

    open fun registerHandler(handler: Class<out EventHandler>, obj: EventHandler?) {
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

    open fun deregisterHandler(handler: Class<out EventHandler>, obj: EventHandler?) {
        handler.methods.forEach { method ->
            deregisterHandleMethod(method, obj)
        }
    }

    open fun deregisterHandler(handler: Class<out EventHandler>) {
        handler.methods.forEach { method ->
            deregisterHandleMethod(method)
        }
    }
}
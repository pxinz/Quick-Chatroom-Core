package org.smdc.quickchatroom.core.event.handler

import java.lang.annotation.Inherited

/**
 * 用于标记事件处理方法
 */
@Target(AnnotationTarget.FUNCTION)
@Inherited
annotation class EventHandlerMethod(val ordinal: Int = 0)

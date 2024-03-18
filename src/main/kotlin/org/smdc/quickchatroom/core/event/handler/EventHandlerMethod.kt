package org.smdc.quickchatroom.core.event.handler

import java.lang.annotation.Inherited

/**
 * 用于标记事件处理方法
 * @param ordinal 注册顺序(当ordinal相等时, 注册顺序随机)
 */
@Target(AnnotationTarget.FUNCTION)
@Inherited
annotation class EventHandlerMethod(val ordinal: Int = 0)


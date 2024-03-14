package event

import org.smdc.quickchatroom.core.event.Event
import org.smdc.quickchatroom.core.event.EventQueue
import org.smdc.quickchatroom.core.event.handler.EventHandler
import org.smdc.quickchatroom.core.event.handler.EventHandlerMethod

fun printf(format: String, vararg objects: Any) {
    System.out.printf(format, *objects)
}

class CustomStringEvent(val string: String) : Event()

class CustomIntEvent(val int: Int) : Event()

class CustomVoidEvent : Event()

class EventTest : EventHandler {
    @EventHandlerMethod(ordinal = 0)
    fun handleString(event: CustomStringEvent) {
        println(event.string)
        event.handle()
    }

    @EventHandlerMethod(ordinal = 1)
    fun wontInvokeHandleString(event: CustomStringEvent) {
        printf("%s (Error invoking!)%n", event.string)
    }

    @EventHandlerMethod(ordinal = 0)
    fun handleInt01(event: CustomIntEvent) {
        printf("%d (Handler 01)%n", event.int)
    }

    @EventHandlerMethod(ordinal = 1)
    fun handle(event: CustomIntEvent) {
        printf("%d (Handler 02)%n", event.int)
    }
}

fun main() {
    val queue = EventQueue()
    val test = EventTest()
    queue.handleMethodsMap.registerHandler(test.javaClass, test)
    queue.add(CustomStringEvent("StringEvent 01"))
    queue.add(CustomStringEvent("StringEvent 02"))
    queue.add(CustomIntEvent(1))
    queue.add(CustomIntEvent(2))
    queue.add(CustomVoidEvent())
    queue.handleAll()
}
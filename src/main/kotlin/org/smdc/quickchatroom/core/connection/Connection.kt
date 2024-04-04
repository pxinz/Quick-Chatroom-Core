package org.smdc.quickchatroom.core.connection

import org.smdc.quickchatroom.core.api.ReturnMap
import org.smdc.quickchatroom.core.connection.events.ConnectionStateChangeEvent
import org.smdc.quickchatroom.core.connection.socket.DataPackage
import org.smdc.quickchatroom.core.connection.socket.sendData
import org.smdc.quickchatroom.core.connection.threads.EventHandleThread
import org.smdc.quickchatroom.core.connection.threads.PackageReadThread
import org.smdc.quickchatroom.core.event.EventQueue
import java.net.Socket
import java.util.Objects

abstract class Connection(
    open val socket: Socket,
    protected open var connectionState: ConnectionState,
    open val eventQueue: EventQueue,
    open val returnMap: ReturnMap,
    protected open val packageReadThread: PackageReadThread,
    protected open val eventHandleThread: EventHandleThread
) {
    protected open var globalRId = 0;
    open fun runConnection() {
        val input = socket.getInputStream()
        if (input.read() == 1 && input.read() == 0) {
            connectionState = ConnectionState.HANDSHAKING
        } else {
            socket.close()
            return
        }
        eventHandleThread.start()
        packageReadThread.start()
    }

    open fun changeState(state: ConnectionState) {
        connectionState = state
        eventQueue.add(ConnectionStateChangeEvent(state))
    }

    open fun sendAndGetLock(pkg: DataPackage): () -> DataPackage? {
        socket.sendData(pkg)
        return returnMap.getLock(pkg.rId)
    }
}
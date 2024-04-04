package org.smdc.quickchatroom.core.test.socket

import org.smdc.quickchatroom.core.connection.socket.receiveData
import org.smdc.quickchatroom.core.connection.socket.sendData
import java.net.ServerSocket
import java.util.Scanner

fun main() {
    val socket = ServerSocket(48148).accept()
    println("Client Connected")
    val scanner = Scanner(System.`in`)
    Thread {
        while (!socket.isClosed) {
            println("Receive: " + String(socket.receiveData()))
        }
    }.start()
    while (!socket.isClosed) {
        val input = scanner.next()
        if (input == "end") {
            socket.close()
        } else {
            socket.sendData(input)
        }
    }
}
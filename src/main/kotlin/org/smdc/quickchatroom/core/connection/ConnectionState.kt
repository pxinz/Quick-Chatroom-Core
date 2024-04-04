package org.smdc.quickchatroom.core.connection

enum class ConnectionState {
    UNCONNECTED,
    HANDSHAKING,
    STATUS,
    LOGIN,
    CHAT
}
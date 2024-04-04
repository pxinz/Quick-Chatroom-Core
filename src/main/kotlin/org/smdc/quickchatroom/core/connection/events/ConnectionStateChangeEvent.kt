package org.smdc.quickchatroom.core.connection.events

import org.smdc.quickchatroom.core.connection.ConnectionState
import org.smdc.quickchatroom.core.event.Event

class ConnectionStateChangeEvent(val state: ConnectionState) : Event()
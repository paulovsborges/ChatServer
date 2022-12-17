package com.pvsb.room

import io.ktor.http.cio.websocket.*

data class Member (
    val userName: String,
    val sessionId: String,
    val webSocket: WebSocketSession
)
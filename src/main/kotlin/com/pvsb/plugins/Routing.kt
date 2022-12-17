package com.pvsb.plugins

import com.pvsb.room.RoomController
import com.pvsb.routes.chatSocket
import com.pvsb.routes.getAllMessages
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val roomController by inject<RoomController>()

    install(Routing) {
        chatSocket(roomController)
        getAllMessages(roomController)
    }
}

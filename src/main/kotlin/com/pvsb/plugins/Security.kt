package com.pvsb.plugins

import com.pvsb.session.ChatSession
import io.ktor.sessions.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.util.*

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<ChatSession>("MY_SESSION")
    }

    intercept(ApplicationCallPipeline.Features) {
        if (call.sessions.get<ChatSession>() == null) {

            val userName = call.parameters["userName"] ?: "Guest"

            call.sessions.set(ChatSession(userName, generateNonce()))
        }
    }
}

package com.pvsb

import com.pvsb.di.mainModule
import com.pvsb.plugins.configureMonitoring
import com.pvsb.plugins.configureRouting
import com.pvsb.plugins.configureSecurity
import com.pvsb.plugins.configureSerialization
import com.pvsb.plugins.configureSockets
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.ext.Koin

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(Koin) {
        modules(mainModule)
    }
    configureSockets()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
}

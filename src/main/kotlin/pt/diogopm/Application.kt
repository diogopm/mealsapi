package pt.diogopm

import io.ktor.server.application.*
import pt.diogopm.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureAdministration()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureStatusPages()
}

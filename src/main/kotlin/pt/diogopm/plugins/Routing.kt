package pt.diogopm.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pt.diogopm.data.MealRepository
import pt.diogopm.domain.MealItemSpec
import pt.diogopm.domain.MealServiceImpl
import pt.diogopm.dto.toDto

fun Application.configureRouting() {

    install(IgnoreTrailingSlash)
    install(AutoHeadResponse)
    install(DoubleReceive)

    routing {

        val service = MealServiceImpl(MealRepository())
        //meals()

        route("/meals") {
            // Get all meals

            get {
                val language: String = call.request.languageOrDefault()
                val allMeals = service.getAllMeals().map { it.toDto(language) }
                call.respond(HttpStatusCode.OK, allMeals)
            }

            // Get meal by [id]
            get("/{id}") {
                val language: String = call.request.languageOrDefault()
                val id = call.parameters["id"] ?: throw IllegalArgumentException("Missing [id]")
                call.respond(service.getMeal(id).toDto(language))
            }

            // Insert meals
            post {
                val meals = call.receive<List<MealItemSpec>>()
                service.insert(meals)
            }

            // delete meal by [id]
            delete("/{id}") {
                TODO("Delete not implemented yet.")
            }
        }
        route("/") {

            get {

                call.respondText("Hello World!")
            }

            post("/double-receive") {
                val first = call.receiveText()
                val theSame = call.receiveText()
                call.respondText("$first $theSame")
            }
        }
    }
}

/*class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()*/



fun ApplicationRequest.languageOrDefault(default: String = "pt") =
    call.request.header(HttpHeaders.AcceptLanguage) ?: default

fun ApplicationRequest.language(): String? = call.request.header(HttpHeaders.AcceptLanguage)
package com.example

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.statusRouting() {
        route("/") {
            get {
            call.respondText("Default", status = HttpStatusCode.OK)
        }

        get ("{health}"){
            call.respondText("status: OK", status = HttpStatusCode.OK)
        }
    }
}

fun Application.registrationStatusRoute(){
    routing{
        statusRouting()
    }
}
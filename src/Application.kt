package com.example

import com.example.dao.RepositoryImpl
import com.example.model.Book
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.gson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database


val dao = RepositoryImpl(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver"))

fun main() {
    embeddedServer(Netty, port = 8080){
        dao.init()
        install(ContentNegotiation){
            gson {}
        }
//        install(FreeMarker){
//            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
//        }
        routing {

//            route("/"){
//                get {
//                    call.respond(FreeMarkerContent("index.ftl", mapOf("books" to dao.getAllBooks())))
//                }
//            }

            route("/"){
                get{
                    call.respond(dao.getAllBooks())
                }
                post{
                    val book = call.receive<Book>()
                    dao.createBook(book.book_name, book.book_author)
                }
                put{
                    val book = call.receive<Book>()
                    dao.updateBook(book.id, book_name = book.book_name, book_author = book.book_author)
                }
                delete("/{id}") {
                    val id = call.parameters["id"]
                    if (id != null){
                        dao.deleteBook(id.toInt())
                    }
                }
            }
        }
    }.start(wait = true)
}



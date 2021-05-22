package com.example.dao

import com.example.model.Book
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class RepositoryImpl(private val db: Database): Repository {

    override fun init() = transaction(db){
        SchemaUtils.create(Books)
        val books = listOf(Book(book_name = "test", book_author = "test", id = 0))
        Books.batchInsert(books){ book ->
            this[Books.id] = book.id
            this[Books.book_name] = book.book_name
            this[Books.book_author] = book.book_author
        }
        Unit
    }

    override fun createBook(book_name: String, book_author: String) {
        Books.insert {
            it[Books.book_name] = book_name
            it[Books.book_author] = book_author
        }
        Unit
    }

    override fun updateBook(id: Int, book_name: String, book_author: String) {
        Books.update({Books.id eq id}){
            it[Books.book_name] = book_name
            it[Books.book_author] = book_author
        }
        Unit
    }

    override fun deleteBook(id: Int) = transaction (db){
        Books.deleteWhere { Books.id eq id }
        Unit
    }

    override fun getBook(id: Int): Book? = transaction(db){
        Books.select{Books.id eq id}.map{
            Book(it[Books.id], it[Books.book_name], it[Books.book_author])
        }.singleOrNull()
    }

    override fun getAllBooks() = transaction(db) {
        Books.selectAll().map{
            Book(it[Books.id], it[Books.book_name], it[Books.book_author])
        }
    }

    override fun close() {}
}
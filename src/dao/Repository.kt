package com.example.dao

import com.example.model.Book
import java.io.Closeable

interface Repository: Closeable {
    fun init()
    fun createBook(book_name: String, book_author: String)
    fun updateBook(id: Int, book_name: String, boo_author: String)
    fun deleteBook(id: Int)
    fun getBook(id: Int): Book?
    fun getAllBooks(): List<Book>
}
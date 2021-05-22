package com.example.dao

import org.jetbrains.exposed.sql.Table


object Books: Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val book_name = varchar("book_name", 50)
    val book_author = varchar("book_author", 50)
}
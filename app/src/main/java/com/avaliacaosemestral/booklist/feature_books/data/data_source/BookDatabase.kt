package com.avaliacaosemestral.booklist.feature_books.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.avaliacaosemestral.booklist.feature_book.data.data_source.BookDao
import com.avaliacaosemestral.booklist.feature_books.domain.model.Book

@Database(
    entities = [Book::class],
    version = 1
)
abstract class BookDatabase: RoomDatabase() {

    abstract val bookDao: BookDao

    companion object {
        const val DATABASE_NAME = "book_db"
    }
}
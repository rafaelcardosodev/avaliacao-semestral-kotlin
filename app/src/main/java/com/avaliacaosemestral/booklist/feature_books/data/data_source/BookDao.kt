package com.avaliacaosemestral.booklist.feature_book.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book")
    fun getBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getBookById(id: Int): Book?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}
package com.avaliacaosemestral.booklist.feature_books.domain.repository

import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    //choose interface for creating fake versions of this repo for testing, making it faster to test

    fun getBooks(): Flow<List<Book>>

    suspend fun getBookById(id: Int): Book?

    suspend fun insertBook(note: Book)

    suspend fun deleteBook(note: Book)
}
package com.avaliacaosemestral.booklist.feature_books.data.repository

import com.avaliacaosemestral.booklist.feature_book.data.data_source.BookDao
import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import com.avaliacaosemestral.booklist.feature_books.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl(
    private val dao: BookDao
) : BookRepository {
    override fun getBooks(): Flow<List<Book>> {
        return dao.getBooks()
    }

    override suspend fun getBookById(id: Int): Book? {
        return dao.getBookById(id)
    }

    override suspend fun insertBook(book: Book) {
        dao.insertBook(book)
    }

    override suspend fun deleteBook(book: Book) {
        dao.deleteBook(book)
    }
}
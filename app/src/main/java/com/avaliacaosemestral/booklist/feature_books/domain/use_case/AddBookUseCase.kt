package com.avaliacaosemestral.booklist.feature_books.domain.use_case

import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import com.avaliacaosemestral.booklist.feature_books.domain.model.InvalidBookException
import com.avaliacaosemestral.booklist.feature_books.domain.repository.BookRepository

class AddBookUseCase(
    private val repository: BookRepository
) {

    @Throws(InvalidBookException::class)
    suspend operator fun invoke(book: Book) {
        if (book.title.isBlank()) {
            throw InvalidBookException("Title of the book can't be empty")
        }
        if (book.gender.isBlank()) {
            throw InvalidBookException("Gender of the book can't be empty")
        }
        if (book.author.isBlank()) {
            throw InvalidBookException("Author of the book can't be empty")
        }
        if (book.totalPages.toString().isBlank()) {
            throw InvalidBookException("Total pages of the book can't be empty")
        }
        if (book.content.isBlank()) {
            throw InvalidBookException("Content of the book can't be empty")
        }
        repository.insertBook(book)
    }
}
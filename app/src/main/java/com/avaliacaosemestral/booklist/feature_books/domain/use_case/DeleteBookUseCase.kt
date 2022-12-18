package com.avaliacaosemestral.booklist.feature_books.domain.use_case

import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import com.avaliacaosemestral.booklist.feature_books.domain.repository.BookRepository

class DeleteBookUseCase(
    private val repository: BookRepository
) {

    suspend operator fun invoke(book: Book) {
        repository.deleteBook(book)
    }
}
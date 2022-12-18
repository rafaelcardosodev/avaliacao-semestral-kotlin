package com.avaliacaosemestral.booklist.feature_books.domain.use_case

import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import com.avaliacaosemestral.booklist.feature_books.domain.repository.BookRepository

class GetBookUseCase(
    private val repository: BookRepository
) {

    suspend operator fun invoke(id: Int): Book? {
        return repository.getBookById(id)
    }
}
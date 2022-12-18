package com.avaliacaosemestral.booklist.feature_books.domain.use_case

import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import com.avaliacaosemestral.booklist.feature_books.domain.repository.BookRepository
import com.avaliacaosemestral.booklist.feature_books.domain.util.BookFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBooksUseCase (
    private val repository: BookRepository
    ) {

    operator fun invoke(
        bookFilter: BookFilter = BookFilter.All()
    ): Flow<List<Book>> {
        return repository.getBooks().map { books ->
            when(bookFilter) {
                is BookFilter.All -> {
                    books.sortedBy { it.title }
                }
                is BookFilter.Read -> {
                    books.filter { it.isRead }
                }
                is BookFilter.NonRead -> {
                    books.filter { !it.isRead }
                }
            }
        }
    }
}
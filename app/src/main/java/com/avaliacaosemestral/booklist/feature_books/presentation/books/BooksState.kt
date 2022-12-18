package com.avaliacaosemestral.booklist.feature_books.presentation.books

import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import com.avaliacaosemestral.booklist.feature_books.domain.util.BookFilter

data class BooksState(
    val books: List<Book> = emptyList(),
    val bookFilter: BookFilter = BookFilter.All(),
    val isOrderSectionVisible: Boolean = false
)

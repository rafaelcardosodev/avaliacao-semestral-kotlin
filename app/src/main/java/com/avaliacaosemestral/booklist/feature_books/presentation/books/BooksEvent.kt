package com.avaliacaosemestral.booklist.feature_books.presentation.books

import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import com.avaliacaosemestral.booklist.feature_books.domain.util.BookFilter

sealed class BooksEvent {
    data class Filter(val bookFilter: BookFilter): BooksEvent()
    data class DeleteBook(val book: Book): BooksEvent()
    object RestoreBook: BooksEvent()
    object ToggleOrderSection: BooksEvent()
}

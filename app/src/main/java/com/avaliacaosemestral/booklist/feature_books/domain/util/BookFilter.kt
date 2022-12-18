package com.avaliacaosemestral.booklist.feature_books.domain.util

sealed class BookFilter {
    class All(): BookFilter()
    class Read(): BookFilter()
    class NonRead(): BookFilter()
}
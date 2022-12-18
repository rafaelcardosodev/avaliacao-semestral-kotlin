package com.avaliacaosemestral.booklist.feature_books.presentation.util

sealed class Screen(val route: String) {
    object BooksScreen: Screen("books_screen")
    object AddEditBookScreen: Screen("add_edit_books_screen")
}
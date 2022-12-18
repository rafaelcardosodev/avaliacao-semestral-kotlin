package com.avaliacaosemestral.booklist.feature_books.domain.use_case

data class BookUseCases(
    val getBooksUseCase: GetBooksUseCase,
    val deleteBookUseCase: DeleteBookUseCase,
    val addBookUseCase: AddBookUseCase,
    val getBookUseCase: GetBookUseCase
)

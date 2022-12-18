package com.avaliacaosemestral.booklist.feature_books.presentation.books

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import com.avaliacaosemestral.booklist.feature_books.domain.use_case.BookUseCases
import com.avaliacaosemestral.booklist.feature_books.domain.util.BookFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val bookUseCases: BookUseCases
) : ViewModel() {

    private val _state = mutableStateOf<BooksState>(BooksState())
    val state: State<BooksState> = _state

    private var recentlyDeletedBook: Book? = null

    private var getBooksJob: Job? = null

    init {
        getBooks(BookFilter.All())
    }

    fun onEvent(event: BooksEvent) {
        when(event) {
            is BooksEvent.Filter -> {
                if(state.value.bookFilter::class == event.bookFilter::class) {
                    return
                }
                getBooks(event.bookFilter)
            }
            is BooksEvent.DeleteBook -> {
                viewModelScope.launch {
                    bookUseCases.deleteBookUseCase(event.book)
                    recentlyDeletedBook = event.book
                }
            }
            is BooksEvent.RestoreBook -> {
                viewModelScope.launch {
                    bookUseCases.addBookUseCase(recentlyDeletedBook ?: return@launch)
                    recentlyDeletedBook = null
                }
            }
            is BooksEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getBooks(bookFilter: BookFilter) {
        getBooksJob?.cancel()
        getBooksJob = bookUseCases.getBooksUseCase(bookFilter)
            .onEach { books ->
                _state.value = state.value.copy(
                    books = books,
                    bookFilter = bookFilter
                )
            }
            .launchIn(viewModelScope)
    }
}
package com.avaliacaosemestral.booklist.feature_books.presentation.add_edit_book

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import com.avaliacaosemestral.booklist.feature_books.domain.model.InvalidBookException
import com.avaliacaosemestral.booklist.feature_books.domain.use_case.BookUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditBookViewModel @Inject constructor(
    private val bookUseCases: BookUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //title field state
    private val _bookTitle = mutableStateOf(BookTextFieldState(
        hint = "Enter book..."
    ))
    val bookTitle: State<BookTextFieldState> = _bookTitle

    //
    private val _bookContent = mutableStateOf(BookTextFieldState(
        hint = "Enter content..."
    ))
    val bookContent: State<BookTextFieldState> = _bookContent

    //gender field state
    private val _bookGender = mutableStateOf(BookTextFieldState(
        hint = "Enter gender..."
    ))
    val bookGender: State<BookTextFieldState> = _bookGender

    //author field state
    private val _bookAuthor = mutableStateOf(BookTextFieldState(
        hint = "Enter author..."
    ))
    val bookAuthor: State<BookTextFieldState> = _bookAuthor

    //total pages field state
    private val _bookTotalPages = mutableStateOf(BookTextFieldState(
        hint = "Enter total pages..."
    ))
    val bookTotalPages: State<BookTextFieldState> = _bookTotalPages

    //last read page field state
    private val _bookLastReadPage = mutableStateOf(BookTextFieldState(
        hint = "Enter last read page..."
    ))
    val bookLastReadPage: State<BookTextFieldState> = _bookLastReadPage

    //book note color state
    private val _bookColor = mutableStateOf(Book.bookColors.random().toArgb())
    val bookColor: State<Int> = _bookColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentBookId: Int? = null

    init {
        savedStateHandle.get<Int>("bookId")?.let { bookId ->
            if (bookId != -1) {
                viewModelScope.launch {
                    bookUseCases.getBookUseCase(bookId)?.also { book ->
                        currentBookId = book.id
                        _bookTitle.value = bookTitle.value.copy(
                            text = book.title,
                            isHintVisible = false
                        )
                        _bookContent.value = bookContent.value.copy(
                            text = book.content,
                            isHintVisible = false
                        )
                        _bookGender.value = bookGender.value.copy(
                            text = book.gender,
                            isHintVisible = false
                        )
                        _bookAuthor.value = bookAuthor.value.copy(
                            text = book.author,
                            isHintVisible = false
                        )
                        _bookTotalPages.value = bookTotalPages.value.copy(
                            text = book.totalPages.toString(),
                            isHintVisible = false
                        )
                        _bookLastReadPage.value = bookLastReadPage.value.copy(
                            text = book.lastReadPage.toString(),
                            isHintVisible = false
                        )
                        _bookColor.value = book.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditBookEvent) {
        when (event) {
            is AddEditBookEvent.EnteredTitle -> {
                _bookTitle.value = bookTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditBookEvent.ChangeTitleFocus -> {
                _bookTitle.value = bookTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            bookTitle.value.text.isBlank()
                )
            }
            is AddEditBookEvent.EnteredGender -> {
                _bookGender.value = bookGender.value.copy(
                    text = event.value
                )
            }
            is AddEditBookEvent.ChangeGenderFocus -> {
                _bookGender.value = bookGender.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            bookGender.value.text.isBlank()
                )
            }
            is AddEditBookEvent.EnteredAuthor -> {
                _bookAuthor.value = bookAuthor.value.copy(
                    text = event.value
                )
            }
            is AddEditBookEvent.ChangeAuthorFocus -> {
                _bookAuthor.value = bookAuthor.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            bookAuthor.value.text.isBlank()
                )
            }
            is AddEditBookEvent.EnteredTotalPages -> {
                _bookTotalPages.value = bookTotalPages.value.copy(
                    text = event.value
                )
            }
            is AddEditBookEvent.ChangeTotalPagesFocus -> {
                _bookTotalPages.value = bookTotalPages.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            bookTotalPages.value.text.isBlank()
                )
            }
            is AddEditBookEvent.EnteredLastReadPage -> {
                _bookLastReadPage.value = bookLastReadPage.value.copy(
                    text = event.value
                )
            }
            is AddEditBookEvent.ChangeLastReadPageFocus -> {
                _bookLastReadPage.value = bookLastReadPage.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            bookLastReadPage.value.text.isBlank()
                )
            }
            is AddEditBookEvent.EnteredContent -> {
                _bookContent.value = bookContent.value.copy(
                    text = event.value
                )
            }
            is AddEditBookEvent.ChangeContentFocus -> {
                _bookContent.value = bookContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            bookContent.value.text.isBlank()
                )
            }
            is AddEditBookEvent.ChangeColor -> {
                _bookColor.value = event.color
            }
            is AddEditBookEvent.SaveBook -> {
                viewModelScope.launch {
                    try {
                        bookUseCases.addBookUseCase(
                            Book (
                                title = bookTitle.value.text,
                                gender = bookGender.value.text,
                                author = bookAuthor.value.text,
                                totalPages = Integer.valueOf(bookTotalPages.value.text),
                                lastReadPage = Integer.valueOf(bookLastReadPage.value.text),
                                isRead = bookTotalPages.value.text == bookLastReadPage.value.text,
                                content = bookContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = bookColor.value,
                                id = currentBookId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveBook)
                    } catch (e: InvalidBookException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save book"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveBook: UiEvent()
    }
}
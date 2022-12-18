package com.avaliacaosemestral.booklist.feature_books.presentation.add_edit_book

import androidx.compose.ui.focus.FocusState

sealed class AddEditBookEvent {
    data class EnteredTitle(val value: String): AddEditBookEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditBookEvent()

    data class EnteredContent(val value: String): AddEditBookEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditBookEvent()

    data class EnteredGender(val value: String): AddEditBookEvent()
    data class ChangeGenderFocus(val focusState: FocusState): AddEditBookEvent()

    data class EnteredAuthor(val value: String): AddEditBookEvent()
    data class ChangeAuthorFocus(val focusState: FocusState): AddEditBookEvent()

    data class EnteredTotalPages(val value: String): AddEditBookEvent()
    data class ChangeTotalPagesFocus(val focusState: FocusState): AddEditBookEvent()

    data class EnteredLastReadPage(val value: String): AddEditBookEvent()
    data class ChangeLastReadPageFocus(val focusState: FocusState): AddEditBookEvent()

    data class ChangeColor(val color: Int): AddEditBookEvent()
    object SaveBook: AddEditBookEvent()
}

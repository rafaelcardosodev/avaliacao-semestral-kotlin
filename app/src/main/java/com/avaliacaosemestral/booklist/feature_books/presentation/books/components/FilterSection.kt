package com.avaliacaosemestral.booklist.feature_books.presentation.books.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.avaliacaosemestral.booklist.feature_books.domain.util.BookFilter

@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    bookFilter: BookFilter = BookFilter.All(),
    onFilterChange: (BookFilter) -> Unit
) {
    Column (
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "All",
                selected = bookFilter is BookFilter.All,
                onSelect = { onFilterChange(BookFilter.All()) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Read",
                selected = bookFilter is BookFilter.Read,
                onSelect = { onFilterChange(BookFilter.Read()) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Non Read",
                selected = bookFilter is BookFilter.NonRead,
                onSelect = { onFilterChange(BookFilter.NonRead()) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
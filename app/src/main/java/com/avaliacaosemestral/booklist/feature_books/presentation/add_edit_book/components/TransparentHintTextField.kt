package com.avaliacaosemestral.booklist.feature_books.presentation.add_edit_book.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions,
    onFocusChange: (FocusState) -> Unit
) {
    Box(modifier = modifier) {
        TextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }
        )
        if (isHintVisible) {
            Text(text = hint, style = textStyle, color = Color.DarkGray)
        }
    }
}
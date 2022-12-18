package com.avaliacaosemestral.booklist.feature_books.presentation.books.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.avaliacaosemestral.booklist.feature_books.domain.model.Book

@Composable
fun BookItem(
    book: Book,
    modifier: Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
    onDeleteClick: () -> Unit
){
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width - cutCornerSize.toPx(), 0f)
                lineTo(size.width, cutCornerSize.toPx())
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(book.color),
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = Color(
                        ColorUtils.blendARGB(book.color, 0x000000, 0.2f)
                    ),
                    topLeft = Offset(size.width - cutCornerSize.toPx(), -100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column() {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(end = 16.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = book.title,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.author,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = book.gender,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1
                )
            }
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = book.totalPages.toString(),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = book.lastReadPage.toString(),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 10
                )
            }
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete book"
            )
        }
    }
}
package com.avaliacaosemestral.booklist.feature_books.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.avaliacaosemestral.booklist.ui.theme.*

@Entity
data class Book (
    val title: String,
    val gender: String,
    val author: String,
    val totalPages: Int,
    val lastReadPage: Int,
    val isRead: Boolean,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
        ) {
    companion object {
        val bookColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidBookException(message: String): Exception(message)
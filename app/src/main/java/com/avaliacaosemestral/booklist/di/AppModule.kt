package com.avaliacaosemestral.booklist.di

import android.app.Application
import androidx.room.Room
import com.avaliacaosemestral.booklist.feature_books.data.data_source.BookDatabase
import com.avaliacaosemestral.booklist.feature_books.data.repository.BookRepositoryImpl
import com.avaliacaosemestral.booklist.feature_books.domain.repository.BookRepository
import com.avaliacaosemestral.booklist.feature_books.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookDatabase(app: Application): BookDatabase {
        return Room.databaseBuilder(
            app,
            BookDatabase::class.java,
            BookDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookRepository(db: BookDatabase): BookRepository {
        return BookRepositoryImpl(db.bookDao)
    }

    @Provides
    @Singleton
    fun provideBookUseCases(repository: BookRepository): BookUseCases {
        return BookUseCases(
            getBooksUseCase = GetBooksUseCase(repository),
            deleteBookUseCase = DeleteBookUseCase(repository),
            addBookUseCase = AddBookUseCase(repository),
            getBookUseCase = GetBookUseCase(repository)
        )
    }
}
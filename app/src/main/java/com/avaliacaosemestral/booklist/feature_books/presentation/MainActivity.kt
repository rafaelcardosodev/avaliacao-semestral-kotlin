package com.avaliacaosemestral.booklist.feature_books.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.avaliacaosemestral.booklist.feature_books.presentation.add_edit_book.AddEditBookScreen
import com.avaliacaosemestral.booklist.feature_books.presentation.books.BooksScreen
import com.avaliacaosemestral.booklist.feature_books.presentation.util.Screen
import com.avaliacaosemestral.booklist.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureNoteAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.BooksScreen.route
                    ) {
                        composable(route = Screen.BooksScreen.route) {
                            BooksScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditBookScreen.route +
                                    "?bookId={bookId}&&bookColor={bookColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "bookId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "bookColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt("bookColor") ?: -1
                            AddEditBookScreen(
                                navController = navController,
                                bookColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}

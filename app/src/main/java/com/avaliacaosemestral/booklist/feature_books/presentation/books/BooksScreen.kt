package com.avaliacaosemestral.booklist.feature_books.presentation.books

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.avaliacaosemestral.booklist.feature_books.presentation.books.components.BookItem
import com.avaliacaosemestral.booklist.feature_books.presentation.books.components.FilterSection
import com.avaliacaosemestral.booklist.feature_books.presentation.util.Screen
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun BooksScreen(
    navController: NavController,
    viewModel: BooksViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddEditBookScreen.route)
            },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add book")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your books",
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(BooksEvent.ToggleOrderSection)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                FilterSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    bookFilter = state.bookFilter,
                    onFilterChange = {
                        viewModel.onEvent(BooksEvent.Filter(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.books) { book ->
                    BookItem(
                        book = book,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditBookScreen.route +
                                            "?bookId=${book.id}&bookColor=${book.color}"
                                )
                            },
                        onDeleteClick = {
                            viewModel.onEvent(BooksEvent.DeleteBook(book))
                            scope.launch {
                               val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Book deleted",
                                    actionLabel = "Undo"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(BooksEvent.RestoreBook)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
package com.avaliacaosemestral.booklist.feature_books.presentation.add_edit_book

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.avaliacaosemestral.booklist.feature_books.domain.model.Book
import com.avaliacaosemestral.booklist.feature_books.presentation.add_edit_book.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditBookScreen(
    navController: NavController,
    bookColor: Int,
    viewModel: AddEditBookViewModel = hiltViewModel()
) {
    val titleState = viewModel.bookTitle.value
    val contentState = viewModel.bookContent.value
    val genderState = viewModel.bookGender.value
    val authorState = viewModel.bookAuthor.value
    val totalPagesState = viewModel.bookTotalPages.value
    val lastReadPageState = viewModel.bookLastReadPage.value

    val scaffoldState = rememberScaffoldState()

    val bookBackgroundAnimatable = remember {
        Animatable (
            Color(if(bookColor != -1) bookColor else viewModel.bookColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditBookViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is AddEditBookViewModel.UiEvent.SaveBook -> {
                    navController.navigateUp()
                }
            }
        }
    }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditBookEvent.SaveBook)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save book")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(bookBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Book.bookColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.bookColor.value == colorInt) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    bookBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(AddEditBookEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                                viewModel.onEvent(AddEditBookEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditBookEvent.ChangeTitleFocus(it))
                },
                keyboardOptions = KeyboardOptions.Default,
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = genderState.text,
                hint = genderState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.EnteredGender(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditBookEvent.ChangeGenderFocus(it))
                },
                keyboardOptions = KeyboardOptions.Default,
                isHintVisible = genderState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = authorState.text,
                hint = authorState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.EnteredAuthor(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditBookEvent.ChangeAuthorFocus(it))
                },
                keyboardOptions = KeyboardOptions.Default,
                singleLine = true,
                isHintVisible = authorState.isHintVisible,
                textStyle = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = totalPagesState.text,
                hint = totalPagesState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.EnteredTotalPages(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditBookEvent.ChangeTotalPagesFocus(it))
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                isHintVisible = totalPagesState.isHintVisible
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = lastReadPageState.text,
                hint = lastReadPageState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.EnteredLastReadPage(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditBookEvent.ChangeLastReadPageFocus(it))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                isHintVisible = lastReadPageState.isHintVisible,
                textStyle = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(8.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditBookEvent.ChangeContentFocus(it))
                },
                keyboardOptions = KeyboardOptions.Default,
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1
            )
        }
    }
}
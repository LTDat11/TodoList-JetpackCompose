package com.example.todolist_jetpackcompose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun TodoListScreen() {
    // Danh sách task giả lập (sẽ thay bằng ViewModel sau)
    val tasks = remember {
        mutableStateListOf(
            "Task 1",
            "Task 2",
            "Task 3,",
            "Task 4",
            "Task 5",
            "Task 6,",
            "Task 7",
            "Task 8",
            "Task 9,"
        )
    }
    var inputText by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }, // Thêm SnackbarHost
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(tasks) { task ->
                    CardCustom(
                        task = task,
                        onDelete = { tasks.remove(task) }
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                InputBar(
                    inputText = inputText,
                    onInputChange = { inputText = it },
                    onAddClick = {
                        if (inputText.isNotEmpty()) {
                            tasks.add(inputText)
                            inputText = ""
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Vui lòng nhập nội dung task!",
                                    actionLabel = "OK"
                                )
                            }
                        }
                    }
                )
            }
        }
    }

}
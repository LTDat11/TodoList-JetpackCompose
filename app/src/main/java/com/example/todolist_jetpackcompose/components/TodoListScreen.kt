package com.example.todolist_jetpackcompose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope

@Preview(showSystemUi = true)
@Composable
fun TodoListScreen() {
    // Danh sách task giả lập (sẽ thay bằng ViewModel sau)
    val tasks = remember { mutableStateListOf("Task 1", "Task 2", "Task 3") }
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                CardCustom(
                    task = task,
                    onDelete = { tasks.remove(task) }
                )
            }
        }
        InputBar(
            inputText = inputText,
            onInputChange = { inputText = it },
            onAddClick = {
                if (inputText.isNotEmpty()) {
                    tasks.add(inputText)
                    inputText = ""
                } else {

                }
            }
        )
    }
}
package com.example.todolist_jetpackcompose.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolist_jetpackcompose.components.CardCustom
import com.example.todolist_jetpackcompose.model.Task
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(navController: NavController) {
    // Danh sách task giả lập
    val tasks = remember {
        mutableStateListOf(
            Task(1, "Task 1", "Nội dung task 1"),
            Task(2, "Task 2", "Nội dung task 2"),
            Task(3, "Task 3", "Nội dung task 3"),
            Task(3, "Task 3", "Nội dung task 3"),
            Task(3, "Task 3", "Nội dung task 3"),
            Task(3, "Task 3", "Nội dung task 3"),
            Task(3, "Task 3", "Nội dung task 3"),
            Task(3, "Task 3", "Nội dung task 3"),
            Task(3, "Task 3", "Nội dung task 3"),
            Task(3, "Task 3", "Nội dung task 3"),
        )
    }

    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }, // Thêm SnackbarHost
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(tasks) { task ->
                    CardCustom(
                        task = task,
                        onDelete = { tasks.remove(task) },
                        onClick = { navController.navigate("task_detail/${task.id}") }

                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(72.dp)
            ) {
                FloatingActionButton(
                    onClick = { showBottomSheet = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Thêm task",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = rememberModalBottomSheetState()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Thêm Task Mới", style = MaterialTheme.typography.headlineSmall)
                        OutlinedTextField(
                            value = titleText,
                            onValueChange = { titleText = it },
                            label = { Text("Tiêu đề") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = contentText,
                            onValueChange = { contentText = it },
                            label = { Text("Nội dung") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                        )
                        
                        Button(
                            onClick = {
                                if (titleText.isNotEmpty()) {
                                    val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
                                    tasks.add(Task(newId, titleText, contentText))
                                    titleText = ""
                                    contentText = ""
                                    showBottomSheet = false
                                } else {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Vui lòng nhập title!",
                                            actionLabel = "Ok",
                                        )
                                    }
                                }
                            },
                        ) {
                            Text("Thêm")
                        }
                    }
                }
            }
        }
    }

}

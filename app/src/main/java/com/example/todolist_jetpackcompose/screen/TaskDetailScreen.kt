package com.example.todolist_jetpackcompose.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todolist_jetpackcompose.domain.model.Task
import com.example.todolist_jetpackcompose.presentaion.ToDoListViewModel

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Long,
    navController: NavController,
    viewModel: ToDoListViewModel = hiltViewModel()
) {

    val tasks by viewModel.tasks.collectAsState()
    val originalTask = tasks.find { it.id == taskId } ?: return // Trở về nếu không tìm thấy task

    var title by remember { mutableStateOf(originalTask.title) }
    var content by remember { mutableStateOf(originalTask.content) }

    // Kiểm tra xem dữ liệu có thay đổi không
    val isDataChanged by remember {
        derivedStateOf {
            title != originalTask.title || content != originalTask.content
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết Task") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = Color.LightGray
                        )
                    }
                }
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Nội dung") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Button(
                onClick = {
                    val updatedTask = originalTask.copy(title = title, content = content)
                    viewModel.updateTask(updatedTask)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isDataChanged && title.isNotEmpty()
            ) {
                Text("Lưu")
            }

        }
    }
}
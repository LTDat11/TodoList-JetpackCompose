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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todolist_jetpackcompose.presentaion.ToDoListViewModel

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Long,
    navController: NavController,
    viewModel: ToDoListViewModel = hiltViewModel()
) {
    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    val titleText by viewModel.titleText.collectAsState()
    val contentText by viewModel.contentText.collectAsState()
    val isUpdateEnabled by viewModel.isUpdateEnabled.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chi tiết Task") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Quay lại",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = titleText,
                onValueChange = { viewModel.onTitleChanged(it) },
                label = { Text("Tiêu đề") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = contentText,
                onValueChange = { viewModel.onContentChanged(it) },
                label = { Text("Nội dung") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Button(
                onClick = {
                    viewModel.onUpdateTask()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isUpdateEnabled
            ) {
                Text("Lưu")
            }
        }
    }
}
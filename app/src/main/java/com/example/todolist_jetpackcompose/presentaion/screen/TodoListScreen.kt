package com.example.todolist_jetpackcompose.presentaion.screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todolist_jetpackcompose.components.CardCustom
import com.example.todolist_jetpackcompose.components.DialogCustom
import com.example.todolist_jetpackcompose.presentaion.ToDoListViewModel
import kotlinx.coroutines.launch

@SuppressLint("UseOfNonLambdaOffsetOverload")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(navController: NavController, viewModel: ToDoListViewModel = hiltViewModel()) {

    val tasks by viewModel.tasks.collectAsState()
    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val showDeleteConfirmDialog by viewModel.showDeleteConfirmDialog.collectAsState()
    val isTaskLoading by viewModel.isTaskLoading.collectAsState()

    // State cho vị trí FAB
    val fabOffsetXState by viewModel.fabOffsetX.collectAsState()
    val fabOffsetYState by viewModel.fabOffsetY.collectAsState()

    // Animatable để tạo hiệu ứng mượt
    val fabOffsetX = remember { Animatable(fabOffsetXState) }
    val fabOffsetY = remember { Animatable(fabOffsetYState) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Surface(
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth(),
            ) {
                CenterAlignedTopAppBar(
                    colors =
                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.primary,
                        ),
                    title = {
                        Text(
                            text = "Todo List",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                        )
                    },
                )
            }
        },
    ) { paddingValues ->
        BoxWithConstraints(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            val screenWidth = maxWidth.value
            val screenHeight = maxHeight.value

            if (isTaskLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp, top = 16.dp),
                ) {
                    items(tasks) { task ->
                        CardCustom(
                            task = task,
                            onClick = { navController.navigate("task_detail/${task.id}") },
                            viewModel = viewModel,
                        )
                    }
                }
            }
            // Fab giống chat bubble của Messenger
            FloatingActionButton(
                onClick = { showBottomSheet = true },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
                modifier =
                    Modifier.offset(x = fabOffsetX.value.dp, y = fabOffsetY.value.dp)
                        .size(56.dp)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragEnd = {
                                    // Khi thả tay, FAB dính vào cạnh trái hoặc phải
                                    coroutineScope.launch {
                                        val fabSize = 56f
                                        val halfScreen = screenWidth / 2
                                        val targetX =
                                            if (fabOffsetX.value < halfScreen) 0f
                                            else (screenWidth - fabSize)
                                        fabOffsetX.animateTo(targetX, animationSpec = tween(300))
                                        // Lưu vị trí sau khi thả
                                        viewModel.updateFabPosition(
                                            fabOffsetX.value,
                                            fabOffsetY.value,
                                        )
                                    }
                                }
                            ) { change, dragAmount ->
                                change.consume()
                                val newOffsetX =
                                    (fabOffsetX.value + dragAmount.x / density).coerceIn(
                                        0f,
                                        screenWidth - 56f,
                                    )
                                val newOffsetY =
                                    (fabOffsetY.value + dragAmount.y / density).coerceIn(
                                        0f,
                                        screenHeight - 56f,
                                    )

                                coroutineScope.launch {
                                    fabOffsetX.snapTo(newOffsetX)
                                    fabOffsetY.snapTo(newOffsetY)
                                }
                            }
                        },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Thêm task",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = rememberModalBottomSheetState(),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Text("Thêm Task Mới", style = MaterialTheme.typography.headlineSmall)
                        OutlinedTextField(
                            value = titleText,
                            onValueChange = { titleText = it },
                            label = { Text("Tiêu đề") },
                            placeholder = { Text("Nhập tiêu đề (bắt buộc)") },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
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
                                    viewModel.addTask(titleText, contentText)
                                    titleText = ""
                                    contentText = ""
                                    showBottomSheet = false
                                }
                            },
                            enabled = titleText.isNotEmpty(),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text("Thêm")
                        }
                    }
                }
            }

            DialogCustom(
                dialogTitle = "Xác nhận xóa",
                dialogText = "Bạn có chắc chắn muốn xóa task này không?",
                icon = Icons.Default.Delete,
                onDismiss = { viewModel.onDismissDeleteDialog() },
                onConfirm = { viewModel.onConfirmDelete() },
                showDialog = showDeleteConfirmDialog,
            )

            // Khôi phục vị trí FAB từ ViewModel khi vào màn hình
            LaunchedEffect(fabOffsetXState, fabOffsetYState) {
                fabOffsetX.snapTo(fabOffsetXState)
                fabOffsetY.snapTo(fabOffsetYState)
                // Đặt vị trí mặc định nếu chưa có (góc dưới bên phải)
                if (fabOffsetXState == 0f && fabOffsetYState == 0f) {
                    fabOffsetX.snapTo(screenWidth - 56f - 16f)
                    fabOffsetY.snapTo(screenHeight - 56f - 16f)
                    viewModel.updateFabPosition(fabOffsetX.value, fabOffsetY.value)
                }
            }
        }

        // View cố định không di chuyển fab được

        //        Box(modifier = Modifier
        //            .padding(paddingValues)
        //            .fillMaxSize()) {
        //            if (isTaskLoading) {
        //                Box(modifier = Modifier.fillMaxSize(), contentAlignment =
        // Alignment.Center) {
        //                    CircularProgressIndicator()
        //                }
        //            } else {
        //                LazyColumn(
        //                    modifier = Modifier
        //                        .padding(horizontal = 16.dp)
        //                        .fillMaxSize(),
        //                    verticalArrangement = Arrangement.spacedBy(8.dp),
        //                    contentPadding = PaddingValues(bottom = 16.dp, top = 16.dp),
        //                ) {
        //                    items(tasks) { task ->
        //                        CardCustom(
        //                            task = task,
        //                            onClick = { navController.navigate("task_detail/${task.id}")
        // },
        //                            viewModel = viewModel,
        //                        )
        //                    }
        //                }
        //                if (showBottomSheet) {
        //                    ModalBottomSheet(
        //                        onDismissRequest = { showBottomSheet = false },
        //                        sheetState = rememberModalBottomSheetState(),
        //                    ) {
        //                        Column(
        //                            modifier = Modifier
        //                                .padding(16.dp)
        //                                .fillMaxWidth(),
        //                            verticalArrangement = Arrangement.spacedBy(16.dp),
        //                        ) {
        //                            Text("Thêm Task Mới", style =
        // MaterialTheme.typography.headlineSmall)
        //                            OutlinedTextField(
        //                                value = titleText,
        //                                onValueChange = { titleText = it },
        //                                label = { Text("Tiêu đề") },
        //                                placeholder = { Text("Nhập tiêu đề (bắt buộc)") },
        //                                modifier = Modifier.fillMaxWidth(),
        //                                maxLines = 1,
        //                            )
        //
        //                            OutlinedTextField(
        //                                value = contentText,
        //                                onValueChange = { contentText = it },
        //                                label = { Text("Nội dung") },
        //                                modifier = Modifier.fillMaxWidth(),
        //                                minLines = 3,
        //                            )
        //
        //                            Button(
        //                                onClick = {
        //                                    if (titleText.isNotEmpty()) {
        //                                        viewModel.addTask(titleText, contentText)
        //                                        titleText = ""
        //                                        contentText = ""
        //                                        showBottomSheet = false
        //                                    }
        //                                },
        //                                enabled = titleText.isNotEmpty(),
        //                                modifier = Modifier.fillMaxWidth(),
        //                            ) {
        //                                Text("Thêm")
        //                            }
        //                        }
        //                    }
        //                }
        //
        //                DialogCustom(
        //                    dialogTitle = "Xác nhận xóa",
        //                    dialogText = "Bạn có chắc chắn muốn xóa task này không?",
        //                    icon = Icons.Default.Delete,
        //                    onDismiss = { viewModel.onDismissDeleteDialog() },
        //                    onConfirm = { viewModel.onConfirmDelete() },
        //                    showDialog = showDeleteConfirmDialog,
        //                )
        //            }
        //        }
    }
}

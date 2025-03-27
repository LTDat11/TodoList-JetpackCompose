package com.example.todolist_jetpackcompose.presentaion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist_jetpackcompose.domain.model.Task
import com.example.todolist_jetpackcompose.domain.usecase.AddTaskUseCase
import com.example.todolist_jetpackcompose.domain.usecase.DeleteTaskUseCase
import com.example.todolist_jetpackcompose.domain.usecase.GetTaskUseCase
import com.example.todolist_jetpackcompose.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val getTaskUseCase: GetTaskUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
) : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // State cho TaskDetailScreen
    private val _selectedTask = MutableStateFlow<Task?>(null)
    //val selectedTask: StateFlow<Task?> = _selectedTask.asStateFlow()

    private val _titleText = MutableStateFlow("")
    val titleText: StateFlow<String> = _titleText.asStateFlow()

    private val _contentText = MutableStateFlow("")
    val contentText: StateFlow<String> = _contentText.asStateFlow()

    private val _isUpdateEnabled = MutableStateFlow(false)
    val isUpdateEnabled: StateFlow<Boolean> = _isUpdateEnabled.asStateFlow()

    // Loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // showDialog
    private val _showConfirmDialog = MutableStateFlow(false)
    val showConfirmDialog: StateFlow<Boolean> = _showConfirmDialog.asStateFlow()

    private val _taskToDelete = MutableStateFlow<Task?>(null)
    val taskToDelete: StateFlow<Task?> = _taskToDelete.asStateFlow()

    // dialog xác nhận xóa
    private val _showDeleteConfirmDialog = MutableStateFlow(false)
    val showDeleteConfirmDialog: StateFlow<Boolean> = _showDeleteConfirmDialog.asStateFlow()

    init {
        viewModelScope.launch {
            getTaskUseCase().collect { tasks ->
                println("ViewModel: Tasks collected = $tasks")
                _tasks.value = tasks
            }
        }
    }

    fun addTask(title: String, content: String) {
        val task = Task(title = title, content = content)
        viewModelScope.launch {
            addTaskUseCase(task)
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
        }
    }

    // Update trạng thái của ták
    fun onTaskStatusChanged(task: Task, isCompleted: Boolean) {
        val updatedTask = task.copy(isCompleted = isCompleted)
        updateTask(updatedTask)
    }

    // Load task khi vào TaskDetailScreen
    fun loadTask(taskId: Long) {
        viewModelScope.launch {
            _isLoading.value = true // Show loading
            _tasks.collect { tasks ->
                //println("ViewModel: Current tasks = $tasks")
                val task = tasks.find { it.id == taskId }
                _selectedTask.value = task
                //println("ViewModel: Found task = $task")
                task?.let {
                    _titleText.value = it.title
                    _contentText.value = it.content
                    updateButtonState()
                }
                _isLoading.value = false
                return@collect
            }
        }
    }

    // Cập nhật title từ UI
    fun onTitleChanged(newTitle: String) {
        _titleText.value = newTitle
        updateButtonState()
    }

    // Cập nhật content từ UI
    fun onContentChanged(newContent: String) {
        _contentText.value = newContent
        updateButtonState()
    }

    // Kiểm tra dữ liệu thay đổi để enable button
    private fun updateButtonState() {
        val task = _selectedTask.value ?: return
        _isUpdateEnabled.value =
            (_titleText.value != task.title || _contentText.value != task.content) && _titleText.value.isNotEmpty()
    }

    fun onUpdateTaskRequested() {
        if (_isUpdateEnabled.value) {
            _showConfirmDialog.value = true // Hiển thị dialog xác nhận
        }
    }

    fun onConfirmUpdate() {
        val task = _selectedTask.value ?: return
        val updatedTask = task.copy(
            title = _titleText.value,
            content = _contentText.value
        )
        updateTask(updatedTask)
        _showConfirmDialog.value = false // Ẩn dialog sau khi xác nhận
    }

    fun onDismissDialog() {
        _showConfirmDialog.value = false // Ẩn dialog khi hủy
    }

    // Xóa task
    fun onDeleteTaskRequested(task: Task) {
        _taskToDelete.value = task
        _showDeleteConfirmDialog.value = true
    }

    fun onConfirmDelete() {
        val task = _taskToDelete.value ?: return
        viewModelScope.launch {
            deleteTaskUseCase(task)
            _showDeleteConfirmDialog.value = false
            _taskToDelete.value = null
        }
    }

    fun onDismissDeleteDialog() {
        _showDeleteConfirmDialog.value = false
        _taskToDelete.value = null
    }
}
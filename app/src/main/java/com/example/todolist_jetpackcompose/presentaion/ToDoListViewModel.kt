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
//    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
) : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        viewModelScope.launch {
            getTaskUseCase().collect { tasks ->
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

    fun updateTask(task: Task) {
        viewModelScope.launch {
            updateTaskUseCase(task)
        }
    }

//    fun deleteTask(task: Task) {
//        viewModelScope.launch {
//            deleteTaskUseCase(task)
//            _tasks.value = getTaskUseCase()
//        }
//    }

    fun onTaskStatusChanged(task: Task, isCompleted: Boolean) {
        val updatedTask = task.copy(isCompleted = isCompleted)
        updateTask(updatedTask)
    }

}
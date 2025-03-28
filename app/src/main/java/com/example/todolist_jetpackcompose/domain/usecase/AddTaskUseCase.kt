package com.example.todolist_jetpackcompose.domain.usecase

import com.example.todolist_jetpackcompose.domain.model.Task
import com.example.todolist_jetpackcompose.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        repository.addTask(task)
    }
}

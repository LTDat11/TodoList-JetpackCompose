package com.example.todolist_jetpackcompose.domain.usecase

import com.example.todolist_jetpackcompose.domain.model.Task
import com.example.todolist_jetpackcompose.domain.repository.TaskRepository

class DeleteTaskUseCase(private val repository: TaskRepository) {
    operator fun invoke(task: Task) {
        return repository.deleteTask(task)
    }
}
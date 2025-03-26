package com.example.todolist_jetpackcompose.domain.usecase

import com.example.todolist_jetpackcompose.domain.model.Task
import com.example.todolist_jetpackcompose.domain.repository.TaskRepository

class GetTaskUseCase(private val repository: TaskRepository) {
    operator fun invoke(): List<Task> {
        return repository.getTask()
    }
}
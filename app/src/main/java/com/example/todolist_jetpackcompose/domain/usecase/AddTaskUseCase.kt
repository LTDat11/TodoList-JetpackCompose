package com.example.todolist_jetpackcompose.domain.usecase

import com.example.todolist_jetpackcompose.domain.model.Task
import com.example.todolist_jetpackcompose.domain.repository.TaskRepository

class AddTaskUseCase(private val repository: TaskRepository) {
    operator fun invoke(task: Task) {
        return repository.addTask(task)
    }
}
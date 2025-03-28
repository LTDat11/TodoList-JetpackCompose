package com.example.todolist_jetpackcompose.domain.usecase

import com.example.todolist_jetpackcompose.domain.model.Task
import com.example.todolist_jetpackcompose.domain.repository.TaskRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getTasks()
    }
}

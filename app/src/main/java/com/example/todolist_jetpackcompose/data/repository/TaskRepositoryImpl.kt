package com.example.todolist_jetpackcompose.data.repository

import com.example.todolist_jetpackcompose.domain.model.Task
import com.example.todolist_jetpackcompose.domain.repository.TaskRepository

class TaskRepositoryImpl : TaskRepository {
    private val tasks = mutableListOf(
        Task(1, "Task 1", "This is task 1"),
        Task(2, "Task 2", "This is task 2"),
        Task(3, "Task 3", "This is task 3"),
    )

    override fun getTask(): List<Task> = tasks.toList()

    override fun addTask(task: Task) {
        tasks.add(task.copy(id = (tasks.maxOfOrNull { it.id } ?: 0) + 1))
    }

    override fun updateTask(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        if (index != -1) tasks[index] = task
    }

    override fun deleteTask(task: Task) {
        tasks.remove(task)
    }
}
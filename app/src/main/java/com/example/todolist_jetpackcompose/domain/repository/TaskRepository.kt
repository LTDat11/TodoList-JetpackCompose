package com.example.todolist_jetpackcompose.domain.repository

import com.example.todolist_jetpackcompose.domain.model.Task

interface TaskRepository {
    fun getTask(): List<Task>
    fun addTask(task: Task)
    fun updateTask(task: Task)
    fun deleteTask(task: Task)
}
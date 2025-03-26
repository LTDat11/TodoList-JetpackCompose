package com.example.todolist_jetpackcompose.model

data class Task(
    val id: Int = 0,
    val title: String,
    val content: String,
    val isCompleted: Boolean = false,
)
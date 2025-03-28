package com.example.todolist_jetpackcompose.domain.model

data class Task(
    val id: Long = 0,
    val title: String,
    val content: String,
    val isCompleted: Boolean = false,
)

package com.example.todolist_jetpackcompose.domain.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Task(
    @Id var id: Long = 0,
    val title: String,
    val content: String,
    val isCompleted: Boolean = false,
)
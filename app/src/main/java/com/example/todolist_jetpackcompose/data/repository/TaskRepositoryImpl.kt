package com.example.todolist_jetpackcompose.data.repository

import android.util.Log
import com.example.todolist_jetpackcompose.ObjectBox
import com.example.todolist_jetpackcompose.domain.model.Task
import com.example.todolist_jetpackcompose.domain.repository.TaskRepository
import io.objectbox.Box
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor() : TaskRepository {
    private val taskBox: Box<Task> by lazy { ObjectBox.store.boxFor(Task::class.java) }

    override fun getTasks(): Flow<List<Task>> = callbackFlow {
        val query = taskBox.query().build()
        val subscription = query.subscribe().observer { tasks -> trySend(tasks) }
        awaitClose { subscription.cancel() }
    }

    override suspend fun addTask(task: Task) {
        Log.d("TaskRepositoryImpl", "Adding task: $task")
        taskBox.put(task)
    }

    override suspend fun updateTask(task: Task) {
        Log.d("TaskRepositoryImpl", "update task: $task")
        taskBox.put(task)
    }

    override suspend fun deleteTask(task: Task) {
        Log.d("TaskRepositoryImpl", "delete task: $task")
        taskBox.remove(task)
    }
}

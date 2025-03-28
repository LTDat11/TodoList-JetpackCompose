package com.example.todolist_jetpackcompose

import android.content.Context
import com.example.todolist_jetpackcompose.domain.model.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    private lateinit var boxStore: BoxStore

    fun init(context: Context) {
        if (!::boxStore.isInitialized) {
            boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()
        }
    }

    val store: BoxStore
        get() = boxStore
}

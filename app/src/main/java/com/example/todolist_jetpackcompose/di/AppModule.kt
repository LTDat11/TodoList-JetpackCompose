package com.example.todolist_jetpackcompose.di

import com.example.todolist_jetpackcompose.data.repository.TaskRepositoryImpl
import com.example.todolist_jetpackcompose.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideTaskRepository(): TaskRepository {
        return TaskRepositoryImpl()
    }
}
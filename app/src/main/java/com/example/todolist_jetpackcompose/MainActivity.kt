package com.example.todolist_jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist_jetpackcompose.presentaion.ToDoListViewModel
import com.example.todolist_jetpackcompose.presentaion.screen.TaskDetailScreen
import com.example.todolist_jetpackcompose.presentaion.screen.TodoListScreen
import com.example.todolist_jetpackcompose.ui.theme.TodoListJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "todo_list") {
                        composable("todo_list") { TodoListScreen(navController = navController) }
                        composable("task_detail/{taskId}") { backStackEntry ->
                            val taskId =
                                backStackEntry.arguments?.getString("taskId")?.toLong() ?: 0L
                            val viewModel = hiltViewModel<ToDoListViewModel>()
                            TaskDetailScreen(
                                navController = navController,
                                viewModel = viewModel,
                                taskId = taskId,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    TodoListJetpackComposeTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Surface(modifier = Modifier.padding(innerPadding)) {}
        }
    }
}

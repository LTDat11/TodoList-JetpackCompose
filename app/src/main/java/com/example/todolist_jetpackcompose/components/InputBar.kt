package com.example.todolist_jetpackcompose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InputBar(
    inputText: String,
    onInputChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = onInputChange,
            label = { Text("Nhập task") },
            modifier = Modifier.weight(1f)
        )
        FloatingActionButton(
            onClick = onAddClick,
            shape = ShapeDefaults.Large,
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 7.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Thêm Task",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
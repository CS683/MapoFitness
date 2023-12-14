package com.example.mapofitness.common.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun SaveButton(onClick: () -> Unit){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = "Save")
    }
}
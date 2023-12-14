package com.example.mapofitness.common.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BottomSheetTitle(onDismiss: () -> Unit, textValue: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(modifier = Modifier.weight(1.5f))
        Text(text = textValue, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onDismiss) {
            Icon(
                Icons.Filled.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
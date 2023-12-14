package com.example.mapofitness.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mapofitness.theme.DarkGray

@Composable
fun MajorContainer(content: @Composable () -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = DarkGray,
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}
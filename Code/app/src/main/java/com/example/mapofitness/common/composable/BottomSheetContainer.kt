package com.example.mapofitness.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mapofitness.theme.Dark

@Composable
fun BottomSheetContainer(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .height(300.dp)
        .background(
            color = Dark,
            shape = RoundedCornerShape(10.dp)
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}
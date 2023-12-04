package com.example.mapofitness.common.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mapofitness.R
import com.example.mapofitness.theme.leftTypography
import com.example.mapofitness.theme.rightTypography

@Composable
fun Logo(){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Mapo", style = leftTypography)
        Spacer(Modifier.width(3.dp))
        Text(text = "Fitness", style = rightTypography)
    }
}
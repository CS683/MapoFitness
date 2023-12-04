package com.example.mapofitness.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.mapofitness.R

val catamaranFontFamily = FontFamily(
    Font(R.font.catamaran_regular)
)
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val leftTypography = TextStyle(
    color = Color(0xFFF8F8F8),
    fontSize = 56.sp,
    fontFamily = catamaranFontFamily,
    fontWeight = FontWeight(700),
    lineHeight = 60.sp,
    textAlign = TextAlign.End
)

val rightTypography = TextStyle(
    color = Color(0xFF79D7FF),
    fontSize = 56.sp,
    fontFamily = catamaranFontFamily,
    fontWeight = FontWeight(700),
    lineHeight = 60.sp
)
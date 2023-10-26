package com.example.mapofitness.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mapofitness.R

private val catamaranFontFamily = FontFamily(
    Font(R.font.catamaran_regular) // replace with actual font resource name if different
)

private val leftTypography = TextStyle(
    color = Color(0xFFF8F8F8),
    fontSize = 56.sp,
    fontFamily = catamaranFontFamily,
    fontWeight = FontWeight(700),
    lineHeight = 60.sp,
    textAlign = TextAlign.End
)
@Composable
fun Start() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.gym),
            contentDescription = null, // decorative
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // This will crop the image if it doesn't fit the screen size
        )

        // Your other UI components go here
        // For instance:
        // Text(text = "Hello, Compose!", Modifier.align(Alignment.Center))
        Text(text = "BE", style = leftTypography)
    }
}

@Preview(showBackground = true, name = "Background Image Screen Preview")
@Composable
fun StartPreview() {
    Start()
}


package com.example.mapofitness.screens.start

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mapofitness.R
import com.example.mapofitness.common.composable.Logo
import com.example.mapofitness.theme.SkyBlue
import com.example.mapofitness.theme.catamaranFontFamily


private val buttonTypography = TextStyle(
    color = Color(0xFF030303),
    fontSize = 20.sp,
    fontFamily = catamaranFontFamily,
    fontWeight = FontWeight(600),
    lineHeight = 32.sp
)
@Composable
fun StartScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.gym),
            contentDescription = null, // decorative
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // This will crop the image if it doesn't fit the screen size
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.7f)) // Adjust alpha for darkness level
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(1f))

            Logo()

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .padding(
                        top = 30.dp,
                        start = 20.dp
                    ) // Positioning via padding; adjust as needed
                    .width(335.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(SkyBlue),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
            ) {
                Text(text = "Get Started", style = buttonTypography)
            }
        }
    }
}

@Preview(showBackground = true, name = "Background Image Screen Preview")
@Composable
fun StartPreview() {
    StartScreen(rememberNavController())
}


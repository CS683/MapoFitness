package com.example.mapofitness.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mapofitness.common.composable.BottomSheetContainer
import com.example.mapofitness.common.composable.BottomSheetTitle
import com.example.mapofitness.common.composable.SaveButton
import com.example.mapofitness.data.local.entity.UserManager
import com.example.mapofitness.theme.GrassGreen

@Composable
fun HeightBottomSheet(onDismiss: () -> Unit, viewModel: PersonalInfoViewModel) {
    var sliderPosition by remember { mutableStateOf(UserManager.getHeight() ?: 100f) }
    BottomSheetContainer {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            BottomSheetTitle(onDismiss = onDismiss, textValue = "Height")
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = String.format("%.1f", sliderPosition),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,

                    )

                Text(
                    text ="cm",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Slider(
                value = sliderPosition,
                onValueChange = { newValue ->
                    sliderPosition = newValue
                },
                valueRange = 55f..250f,
                modifier = Modifier.height(40.dp),
                colors = SliderDefaults.colors(
                    thumbColor = GrassGreen,
                    activeTrackColor = Color.Gray,
                    inactiveTrackColor = Color.Gray,
                    activeTickColor = Color.Blue, // For steps slider
                    inactiveTickColor = Color.Magenta // For steps slider
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            SaveButton{ onSave(sliderPosition, viewModel, onDismiss) }
        }
    }
}

private fun onSave(height: Float, viewModel: PersonalInfoViewModel, onDismiss: () -> Unit) {
    UserManager.setHeight(height)
    UserManager.setUserData()
    viewModel.updateHeight(height)
    onDismiss()
}

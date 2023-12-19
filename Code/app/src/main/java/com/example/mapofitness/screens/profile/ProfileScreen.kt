package com.example.mapofitness.screens.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScopeMarker
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mapofitness.R
import com.example.mapofitness.data.local.entity.UserManager
import com.example.mapofitness.theme.GrassGreen
import com.example.mapofitness.theme.LightPurple
import com.example.mapofitness.theme.NavyBlue


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    onSignOut: () -> Unit,
    onPersonalInfo: () -> Unit,
    viewModel: ProfileViewModel
) {
    val totalWorkout by viewModel.totalWorkout.collectAsState()
    val calorieBurned by viewModel.calorieBurned.collectAsState()
    val weightRecordCount by viewModel.weightRecordsCount.collectAsState()

    viewModel.fetchTotalWorkout()
    viewModel.fetchCalorieBurned()
    viewModel.fetchWeightRecordCount()


    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
            .fillMaxWidth()
        ){
            IconButton(onClick = { /*TODO*/ }){
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Settings"
                )
            }

        }
        val userData = UserManager.currentUser.value
        if(userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if(userData?.username != null) {
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        StatsSection(
            totalWorkouts = totalWorkout,
            totalCalories = calorieBurned,
            weightRecordCount = weightRecordCount
        )
        PersonalInfoButton(onPersonalInfo)
        Spacer(modifier = Modifier.weight(1f))
        SignOutButton(onSignOut)
        Spacer(modifier = Modifier.weight(0.5f))
    }
}



@Composable
fun StatsSection(totalWorkouts: Int, totalCalories: Float, weightRecordCount: Int) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "My statistics", style = MaterialTheme.typography.titleLarge)
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            StatItem(label = "Workouts total", value = totalWorkouts.toString(), R.drawable.chart_bar, LightPurple)
            StatItem(label = "Calories burned", value = String.format("%.1f", totalCalories), R.drawable.fire, NavyBlue)
            StatItem(label = "Weight Records Total", value = weightRecordCount.toString(), R.drawable.streak, GrassGreen)
        }
    }
}

@Composable
fun StatItem(label: String, value: String, @DrawScopeMarker iconId: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "SVG Icon",
            modifier = Modifier.size(32.dp),
            tint = color
        )
        Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        label.split(" ").forEach { word ->
            Text(text = word, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun SignOutButton(onSignOut: () -> Unit){
    Button(
        onClick = onSignOut,
        //colors = ButtonDefaults.buttonColors(SkyBlue),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = "Sign out")
    }
}

@Composable
fun PersonalInfoButton(onPersonalInfo: () -> Unit){
    Button(
        modifier = Modifier.
        fillMaxWidth(),
        onClick = onPersonalInfo,
        shape = RoundedCornerShape(16.dp)
    ){
        Text(text = "Personal Information")
    }
}

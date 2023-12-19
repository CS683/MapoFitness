package com.example.mapofitness.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.mapofitness.common.composable.BottomSheetContainer
import com.example.mapofitness.common.composable.BottomSheetTitle
import com.example.mapofitness.common.composable.MajorContainer
import com.example.mapofitness.data.local.entity.User
import com.example.mapofitness.data.local.entity.UserManager
import com.example.mapofitness.data.local.entity.WeightRecord
import com.example.mapofitness.screens.dashboard.DashboardScreen
import com.example.mapofitness.screens.dashboard.DashboardViewModel
import com.example.mapofitness.theme.Dark
import com.example.mapofitness.theme.GrassGreen
import com.example.mapofitness.theme.White
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel, dashboardViewModel: DashboardViewModel, addWeightSheetState: ModalBottomSheetState, onActivity: () -> Unit){
    val userData = UserManager.currentUser.value
    val isLoading = remember { mutableStateOf(true)}
    LaunchedEffect(key1 = true) {
        UserManager.fetchUserData()
        isLoading.value = false
    }
    ChildHomeScreen(userData = userData, isLoading = isLoading.value, homeViewModel, dashboardViewModel, addWeightSheetState, onActivity)
}


@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChildHomeScreen(userData: User?, isLoading: Boolean, homeViewModel: HomeViewModel, dashboardViewModel: DashboardViewModel, addWeightSheetState: ModalBottomSheetState, onActivity: () -> Unit) {
    val weightRecords by homeViewModel.weightRecords.collectAsState()
    val weight by homeViewModel.weight.collectAsState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    homeViewModel.fetchWeight()
    homeViewModel.fetchRecentWeightRecords()

    if(isLoading){
        CircularProgressIndicator(color = White)
    }else if (userData != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (userData.profilePictureUrl != null) {
                    AsyncImage(
                        model = userData.profilePictureUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    val time = getTimeOfDay()
                    Text(
                        text = "Good $time,",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    if (userData.username != null) {
                        Text(text = userData.username, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.heightIn(16.dp))


            DashboardScreen(dashboardViewModel)

            Spacer(modifier = Modifier.heightIn(16.dp))

            MajorContainer {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Weight Record",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = { coroutineScope.launch { addWeightSheetState.show() } },
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .padding(2.dp)
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Add",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = String.format("%.1f", weight),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text ="lbs",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    LineChartComposable(weightRecords = weightRecords)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            WorkOutComponent(onActivity)

        }
    }
}


@Composable
fun LineChartComposable(weightRecords: List<WeightRecord>) {
    val entries = weightRecords.mapIndexed { index, weightRecord ->
        // Convert your WeightRecord into Entry
        Entry(index.toFloat(), weightRecord.weight)
    }
    val dataSet = LineDataSet(entries, "Label").apply {
        lineWidth = 2.5f // or any other float value you prefer for thickness

        // Enable or disable the drawing of circle indicators for each entry
        setDrawCircles(true)

        // Set the circle radius (float value, where the default is 4f)
        circleRadius = 3f // or any other float value you prefer for size

        // Customize the circle colors, if desired
        setCircleColor(-1)
    }

    // Customize dataSet here (colors, values formatter, etc.)

    val lineData = LineData(dataSet)

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        factory = { context ->
            LineChart(context).apply {
                data = lineData

                description.isEnabled = false
                legend.isEnabled = false

                xAxis.apply {
                    isEnabled = true
                    setDrawGridLines(false)
                    setDrawLabels(false)
                }

                axisLeft.apply {
                    isEnabled = true
                    setDrawGridLines(false)
                    setDrawLabels(false)
                }

                axisRight.apply {
                    isEnabled = false
                    setDrawGridLines(false)
                    setDrawLabels(false)
                }

                dataSet.setDrawValues(false)
                dataSet.setDrawCircles(false)
                //setBackgroundColor(-1)
                // Further customization
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddWeightBottomSheetContent(onDismiss: () -> Unit, homeViewModel: HomeViewModel) {
    val now = getTime()
    BottomSheetContainer {
        BottomSheetTitle(onDismiss = onDismiss, textValue = "Today: ${getHourMinute()}")
        Spacer(modifier = Modifier.height(20.dp))
        WeightSlider(now, homeViewModel)

    }
}



@Composable
fun WeightSlider(now: String, viewModel: HomeViewModel) {
    var sliderPosition by remember { mutableStateOf(100f) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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
                text ="lbs",
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
            valueRange = 55f..440f,
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
        Button(
           onClick = { onAddWeight(now, sliderPosition, viewModel) }
        ){
            Text("Save", color = Dark)
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}


fun onAddWeight(now: String, sliderPosition: Float, homeViewModel: HomeViewModel){
    val weightRecord = WeightRecord(now, sliderPosition)
    UserManager.addWeightRecord(weightRecord)
    UserManager.setWeight(sliderPosition)
    UserManager.getWeightRecordCount()?.plus(1)?.let { UserManager.setWeightRecordCount(it) }
    UserManager.setUserData()
    homeViewModel.updateweight(sliderPosition)
    homeViewModel.fetchRecentWeightRecords()
}
@RequiresApi(Build.VERSION_CODES.O)
fun getTime(): String {
    val currentDateTime = LocalDateTime.now()
    return currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}

@RequiresApi(Build.VERSION_CODES.O)
fun getHourMinute(): String {
    val currentDateTime = LocalDateTime.now()
    return currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
}

@RequiresApi(Build.VERSION_CODES.O)
fun getTimeOfDay(): String {
    val hour = LocalTime.now().hour

    return when {
        hour < 12 -> "Morning"
        hour < 18 -> "Afternoon"
        else -> "Evening"
    }
}

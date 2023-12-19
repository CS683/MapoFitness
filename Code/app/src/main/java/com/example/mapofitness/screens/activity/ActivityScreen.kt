package com.example.mapofitness.screens.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mapofitness.common.composable.SearchBar
import com.example.mapofitness.data.local.entity.Activity
import com.example.mapofitness.data.local.entity.UserManager
import com.example.mapofitness.theme.Dark
import com.example.mapofitness.theme.DarkGray
import com.example.mapofitness.theme.SkyBlue
import com.example.mapofitness.theme.White
import kotlinx.coroutines.launch
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ActivityScreen(
    onBack: () -> Unit,
    viewModel: ActivityViewModel
) {
    val activityList by viewModel.activityList.observeAsState()
    val scrollState = rememberScrollState()
    val addWorkoutSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var selectedActivity by remember { mutableStateOf<Activity?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    ModalBottomSheetLayout(
        sheetState = addWorkoutSheetState,
        sheetContent = {
            if (selectedActivity != null) {
                ActivityBottomSheet(onDismiss = {coroutineScope.launch { addWorkoutSheetState.hide() }}, selectedActivity!!)
            } else {
                Text("Select an activity")
            }
        },
        sheetBackgroundColor = Dark,
        sheetShape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            TopAppBar(
                title = { Text("Work Out Record") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Dark)
            )
            SearchBar(onQueryChanged = { query -> searchQuery = query }, searchQuery = searchQuery)
            activityList?.let { activities ->
                val filteredActivities = if (searchQuery.isEmpty()) {
                    activities
                } else {
                    activities.filter {
                        it.activity?.replace("\\s".toRegex(), "")
                            ?.contains(searchQuery.replace("\\s".toRegex(), ""), ignoreCase = true) ?: false
                    }
                }
                ActivityList(filteredActivities) { activity ->
                    selectedActivity = activity
                    coroutineScope.launch { addWorkoutSheetState.show() }
                }
            }
        }
    }
}

@Composable
fun ActivityList(list: List<Activity>, onActivityClick: (Activity) -> Unit) {
    Column {
        list.forEach{ activity ->
            val calorie = getCalorie(activity)
            ActivityItemRow(activity = activity.activity, calorie = calorie) {
                onActivityClick(activity)
            }
        }
    }
}


@Composable
fun ActivityItemRow(activity: String?, calorie: Int?, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(5.dp)
            .border(
                width = 2.dp, // Thickness of the border
                color = DarkGray, // Color of the border
                shape = RoundedCornerShape(8.dp) // Corner radius
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (activity != null) {
            Text(
                text = activity,
                color = White,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = calorie.toString(),
            color = SkyBlue,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = " Cal/hour",
            color = White,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Go to setting",
            tint = DarkGray
        )
    }
}

fun getCalorie(activity: Activity): Int? {
    val weight = UserManager.getWeight() ?: 155f
    val standardWeights = listOf(134, 155, 180, 205)
    return when(standardWeights.minByOrNull { abs(it - weight) }){
        130 -> activity.calories?.`134`
        155 -> activity.calories?.`155`
        180 -> activity.calories?.`180`
        else -> {
            activity.calories?.`205`
        }
    }
}
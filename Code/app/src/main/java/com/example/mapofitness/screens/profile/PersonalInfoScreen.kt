package com.example.mapofitness.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mapofitness.common.composable.MajorContainer
import com.example.mapofitness.data.local.entity.Gender
import com.example.mapofitness.theme.Dark
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PersonalInfoScreen(
    onBack: () -> Unit,
    viewModel: PersonalInfoViewModel
) {
    var currentBottomSheet by remember { mutableStateOf(PersonalInfoBottomSheetType.NONE) }
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val gender by viewModel.gender.collectAsState()
    val dob by viewModel.dob.collectAsState()
    val height by viewModel.height.collectAsState()
    val weight by viewModel.weight.collectAsState()

    Column {
        TopAppBar(
            title = { Text("Personal Information") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = topAppBarColors(containerColor = Dark)
        )


        Spacer(modifier = Modifier.height(8.dp))
        ModalBottomSheetLayout(
            sheetState = bottomSheetState,
            sheetContent = {
                when (currentBottomSheet) {
                    PersonalInfoBottomSheetType.GENDER -> GenderBottomSheet(onDismiss = {coroutineScope.launch { bottomSheetState.hide() }}, viewModel)
                    PersonalInfoBottomSheetType.DOB -> DOBBottomSheet(onDismiss = {coroutineScope.launch { bottomSheetState.hide() }}, viewModel)
                    PersonalInfoBottomSheetType.HEIGHT -> HeightBottomSheet(onDismiss = {coroutineScope.launch { bottomSheetState.hide() }}, viewModel)
                    PersonalInfoBottomSheetType.WEIGHT -> WeightBottomSheet(onDismiss = {coroutineScope.launch { bottomSheetState.hide() }}, viewModel)
                    else -> {}
                }
            },
            sheetBackgroundColor = Dark,
            sheetShape = RoundedCornerShape(20.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                MajorContainer {
                    LazyColumn {
                        item {
                            SettingItem(
                                label = "Gender",
                                value = if (gender == Gender.UNKNOWN) "" else gender.toString()
                            ) {
                                coroutineScope.launch {
                                    currentBottomSheet = PersonalInfoBottomSheetType.GENDER
                                    bottomSheetState.show()
                                }
                            }
                        }
                        item {
                            SettingItem(
                                label = "Date of Birth",
                                value = dob
                            ) {
                                coroutineScope.launch {
                                    currentBottomSheet = PersonalInfoBottomSheetType.DOB
                                    bottomSheetState.show()
                                }
                            }
                        }
                        item {
                            SettingItem(
                                label = "Height",
                                value = if (height == 0f) " cm" else String.format("%.1f cm", height)
                            ) {
                                coroutineScope.launch {
                                    currentBottomSheet = PersonalInfoBottomSheetType.HEIGHT
                                    bottomSheetState.show()
                                }
                            }
                        }
                        item {
                            SettingItem(
                                label = "Weight",
                                value = if (weight == 0f) " lbs" else String.format("%.1f lbs", weight)
                            ) {
                                coroutineScope.launch {
                                    currentBottomSheet = PersonalInfoBottomSheetType.WEIGHT
                                    bottomSheetState.show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SettingItem(label: String, value: String, onClick: ()-> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Text(text = value)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "More",
            modifier = Modifier.size(24.dp),
            tint = LocalContentColor.current.copy(alpha = 0.3f)
        )
    }
}

enum class PersonalInfoBottomSheetType {
    NONE,
    GENDER,
    DOB,
    HEIGHT,
    WEIGHT
}


fun lastDayInMonth(month: Int, year: Int): Int {
    return if (month != 2) {
        31 - (month - 1) % 7 % 2
    } else {
        if (year and 3 == 0 && (year % 25 != 0 || year and 15 == 0)) {
            29
        } else {
            28
        }
    }
}


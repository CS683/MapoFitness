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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mapofitness.common.composable.BottomSheetContainer
import com.example.mapofitness.common.composable.BottomSheetTitle
import com.example.mapofitness.common.composable.MajorContainer
import com.example.mapofitness.data.local.entity.Gender
import com.example.mapofitness.data.local.entity.UserManager
import com.example.mapofitness.theme.Dark
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PersonalInfoScreen(
    onBack: () -> Unit
) {
    var currentBottomSheet by remember { mutableStateOf(PersonalInfoBottomSheetType.NONE) }
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val user = UserManager.currentUser.value
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
                    PersonalInfoBottomSheetType.GENDER -> GenderBottomSheet(onDismiss = {coroutineScope.launch { bottomSheetState.hide() }})
                    PersonalInfoBottomSheetType.DOB -> DOBBottomSheet(onDismiss = {coroutineScope.launch { bottomSheetState.hide() }})
                    PersonalInfoBottomSheetType.HEIGHT -> HeightBottomSheet(onDismiss = {coroutineScope.launch { bottomSheetState.hide() }})
                    PersonalInfoBottomSheetType.WEIGHT -> WeightBottomSheet(onDismiss = {coroutineScope.launch { bottomSheetState.hide() }})
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
                        val height = if (user?.height != null) user.height else ""
                        val weight = if (user?.weight != null) user.weight else ""
                        item {
                            SettingItem(
                                label = "Gender",
                                value = if (user?.gender == Gender.UNKNOWN) "" else user?.gender.toString()
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
                                value = if (user?.dob == null) "" else user.dob!!
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
                                value = "$height cm"
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
                                value = "$weight lbs"
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

@Composable
fun GenderBottomSheet(onDismiss: () -> Unit) {
    BottomSheetContainer {
        BottomSheetTitle(onDismiss = onDismiss, textValue = "Gender")
    }
}

@Composable
fun HeightBottomSheet(onDismiss: () -> Unit) {
    BottomSheetContainer {
        BottomSheetTitle(onDismiss = onDismiss, textValue = "Height")
    }
}

@Composable
fun WeightBottomSheet(onDismiss: () -> Unit) {
    BottomSheetContainer {
        BottomSheetTitle(onDismiss = onDismiss, textValue = "Weight")
    }
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


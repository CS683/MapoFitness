package com.example.mapofitness.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mapofitness.common.composable.BottomSheetContainer
import com.example.mapofitness.common.composable.BottomSheetTitle
import com.example.mapofitness.common.composable.InfiniteCircularList
import com.example.mapofitness.common.composable.SaveButton
import com.example.mapofitness.data.local.entity.Gender
import com.example.mapofitness.data.local.entity.UserManager
import com.example.mapofitness.theme.SkyBlue

@Composable
fun GenderBottomSheet(onDismiss: () -> Unit , viewModel: PersonalInfoViewModel) {
    var gender by remember {
        mutableStateOf(getGender(UserManager.getGender()))
    }
    BottomSheetContainer {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            BottomSheetTitle(onDismiss = onDismiss, textValue = "Gender")
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                InfiniteCircularList(
                    width = 100.dp,
                    itemHeight = 40.dp,
                    items = listOf("Other", "Female", "Male"),
                    initialItem = gender,
                    textStyle = TextStyle(fontSize = 13.sp),
                    textColor = Color.LightGray,
                    selectedTextColor = SkyBlue,
                    onItemSelected = { i, item ->
                        gender = item
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            SaveButton{ onSave(gender, viewModel, onDismiss) }
        }

    }
}

private fun onSave(gender: String, viewModel: PersonalInfoViewModel, onDismiss: () -> Unit) {
    val currentGender = when(gender) {
        "Male" -> Gender.MALE
        "Female" -> Gender.FEMALE
        else -> { Gender.UNKNOWN }
    }
    UserManager.setGender(currentGender)
    UserManager.setUserData()
    viewModel.updateGender(currentGender)
    onDismiss()
}

private fun getGender(gender: Gender): String {
    return when(gender) {
        Gender.FEMALE -> "Female"
        Gender.MALE -> "Male"
        else -> { "Other" }
    }
}
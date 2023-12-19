package com.example.mapofitness.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
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
import com.example.mapofitness.data.local.entity.UserManager
import com.example.mapofitness.theme.Dark
import com.example.mapofitness.theme.SkyBlue
import java.text.SimpleDateFormat
import java.util.Calendar

@Composable
fun DOBBottomSheet(onDismiss: () -> Unit, viewModel: PersonalInfoViewModel) {
    BottomSheetContainer {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Dark
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                var day by remember {
                    mutableStateOf(getDay(UserManager.getDOB()) ?: 1)
                }
                var month by remember {
                    mutableStateOf(getMonth(UserManager.getDOB()) ?: 1)
                }
                var year by remember {
                    mutableStateOf(getYear(UserManager.getDOB()) ?: 2023)
                }
                var lastDayInMonth by remember {
                    mutableStateOf(30)
                }

                fun adjustDay() {
                    val newLastDayInMonth = lastDayInMonth(month, year)
                    if (lastDayInMonth != newLastDayInMonth) {
                        lastDayInMonth = newLastDayInMonth
                        if (day > newLastDayInMonth) {
                            day = lastDayInMonth
                        }
                    }
                }

                BottomSheetTitle(onDismiss = onDismiss, textValue = "Date of Birth")
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfiniteCircularList(
                        width = 50.dp,
                        itemHeight = 40.dp,
                        items = (1..lastDayInMonth).toMutableList(),
                        initialItem = day,
                        textStyle = TextStyle(fontSize = 13.sp),
                        textColor = Color.LightGray,
                        selectedTextColor = SkyBlue,
                        onItemSelected = { i, item ->
                            day = item
                        }
                    )
                    InfiniteCircularList(
                        width = 100.dp,
                        itemHeight = 40.dp,
                        items = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"), //(1..30).map { it.toString() },
                        initialItem = month,
                        textStyle = TextStyle(fontSize = 13.sp),
                        textColor = Color.LightGray,
                        selectedTextColor = SkyBlue,
                        onItemSelected = { i, item ->
                            month = i + 1
                            adjustDay()
                        }
                    )
                    InfiniteCircularList(
                        width = 50.dp,
                        itemHeight = 40.dp,
                        items = (1900..2023).toList(),
                        initialItem = year,
                        textStyle = TextStyle(fontSize = 13.sp),
                        textColor = Color.LightGray,
                        selectedTextColor = SkyBlue,
                        onItemSelected = { i, item ->
                            year = item
                            adjustDay()
                        }
                    )

                }
                Spacer(modifier = Modifier.weight(1f))
                SaveButton{ onSave(day, month, year, viewModel, onDismiss) }
            }
        }

    }

}

private fun onSave(day: Int, month: Int, year: Int, viewModel: PersonalInfoViewModel, onDismiss: () -> Unit) {
    val dob = formatDate(year, month, day)
    UserManager.setDOB(dob)
    UserManager.setUserData()
    viewModel.updateDOB(dob)
    onDismiss()
}

fun formatDate(year: Int, month: Int, day: Int): String {
    return String.format("%04d-%02d-%02d", year, month, day)
}

fun getYear(dateString: String?): Int? {
    if(dateString == null){
        return null
    }
    val format = SimpleDateFormat("yyyy-MM-dd")
    val date = format.parse(dateString)
    val calendar = Calendar.getInstance().apply {
        time = date
    }

    return calendar.get(Calendar.YEAR)
}

fun getMonth(dateString: String?): Int? {
    if(dateString == null){
        return null
    }
    val format = SimpleDateFormat("yyyy-MM-dd")
    val date = format.parse(dateString)
    val calendar = Calendar.getInstance().apply {
        time = date
    }
    return calendar.get(Calendar.MONTH) + 1
}

fun getDay(dateString: String?): Int? {
    if(dateString == null){
        return null
    }
    val format = SimpleDateFormat("yyyy-MM-dd")
    val date = format.parse(dateString)
    val calendar = Calendar.getInstance().apply {
        time = date
    }

    return calendar.get(Calendar.DAY_OF_MONTH)
}


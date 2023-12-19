package com.example.mapofitness.screens.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
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
import com.example.mapofitness.data.local.entity.Activity
import com.example.mapofitness.data.local.entity.ActivityRecord
import com.example.mapofitness.data.local.entity.UserManager
import com.example.mapofitness.screens.home.getTime
import com.example.mapofitness.theme.SkyBlue

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivityBottomSheet(onDismiss: () -> Unit , activity: Activity) {
    var minute by remember {
        mutableStateOf(0)
    }
    var hour by remember {
        mutableStateOf(0)
    }
    BottomSheetContainer {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            BottomSheetTitle(onDismiss = onDismiss, textValue = activity.activity!!)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(),  verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.weight(1f))
                InfiniteCircularList(
                    width = 50.dp,
                    itemHeight = 40.dp,
                    items = (0..23).toList(),
                    initialItem = hour,
                    textStyle = TextStyle(fontSize = 13.sp),
                    textColor = Color.LightGray,
                    selectedTextColor = SkyBlue,
                    onItemSelected = { i, item ->
                        hour = item
                    }
                )
                Text("Hour")
                Spacer(modifier = Modifier.width(30.dp))
                InfiniteCircularList(
                    width = 50.dp,
                    itemHeight = 40.dp,
                    items = (0..59).toList(),
                    initialItem = minute,
                    textStyle = TextStyle(fontSize = 13.sp),
                    textColor = Color.LightGray,
                    selectedTextColor = SkyBlue,
                    onItemSelected = { i, item ->
                        minute = item
                    }
                )
                Text("Minutes")
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
            SaveButton{ onSave(activity, hour, minute, onDismiss) }
        }

    }
}



@RequiresApi(Build.VERSION_CODES.O)
private fun onSave(activity: Activity, hour: Int, minute: Int, onDismiss: () -> Unit) {
    val time = hour * 60 + minute
    val calorie = getCalorie(activity)?.times((time / 60f))
    val date = getTime()
    UserManager.addActivityRecord(
        ActivityRecord(date, activity.activity!!, time, calorie!!)
    )
    UserManager.getTotalWorkout()?.plus(1)?.let { UserManager.setTotalWorkout(it) }
    UserManager.getCalorieBurned()?.plus(calorie)?.let { UserManager.setCalorieBurned(it) }
    UserManager.setUserData()
    onDismiss()
}

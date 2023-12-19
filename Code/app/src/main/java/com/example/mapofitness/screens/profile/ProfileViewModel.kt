package com.example.mapofitness.screens.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.mapofitness.data.local.entity.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@RequiresApi(Build.VERSION_CODES.O)
class ProfileViewModel : ViewModel() {
    private val _totalWorkout = MutableStateFlow<Int>(0)
    val totalWorkout: StateFlow<Int> = _totalWorkout

    private val _calorieBurned = MutableStateFlow<Float>(0f)
    val calorieBurned: StateFlow<Float> = _calorieBurned

    private val _weightRecordsCount = MutableStateFlow<Int>(0)
    val weightRecordsCount: StateFlow<Int> = _weightRecordsCount

    init {
        fetchTotalWorkout()
        fetchCalorieBurned()
        fetchWeightRecordCount()
    }

    fun fetchTotalWorkout() {
        _totalWorkout.value = UserManager.getTotalWorkout()!!
    }

    fun fetchCalorieBurned() {
        _calorieBurned.value = UserManager.getCalorieBurned()!!
    }

    fun fetchWeightRecordCount() {
        _weightRecordsCount.value = UserManager.getWeightRecordCount()!!
    }



}
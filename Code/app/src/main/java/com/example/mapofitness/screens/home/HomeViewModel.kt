package com.example.mapofitness.screens.home

import androidx.lifecycle.ViewModel
import com.example.mapofitness.data.local.entity.UserManager
import com.example.mapofitness.data.local.entity.WeightRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _weightRecords = MutableStateFlow<List<WeightRecord>>(emptyList())
    val weightRecords: StateFlow<List<WeightRecord>> = _weightRecords

    private val _weight = MutableStateFlow<Float>(2f)
    val weight: StateFlow<Float> = _weight
    init {
        fetchRecentWeightRecords()
        fetchWeight()
    }
    fun fetchRecentWeightRecords() {
        UserManager.fetchRecentWeightRecords() { records ->
            _weightRecords.value = records
        }
    }

    fun fetchWeight(){
        val currentWeight = UserManager.getWeight()
        if (currentWeight != null) {
            _weight.value = currentWeight
        }else{
            _weight.value = 4f
        }

    }

    fun updateweight(newWeight: Float){
        _weight.value = newWeight
    }
}



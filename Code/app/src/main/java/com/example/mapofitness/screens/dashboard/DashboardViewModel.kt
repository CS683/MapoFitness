package com.example.mapofitness.screens.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mapofitness.data.local.entity.Gender
import com.example.mapofitness.data.local.entity.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.pow

class DashboardViewModel : ViewModel() {

    private val _bmi = MutableStateFlow<Float>(1f)
    val bmi: StateFlow<Float> = _bmi

    private val _bodyFat = MutableStateFlow<Float>(1f)
    val bodyFat: StateFlow<Float> = _bodyFat

    private val _metabolism = MutableStateFlow<Float>(1f)
    val metabolism: StateFlow<Float> = _metabolism

    init {
        updateBmi()
        updateBodyFat()
        updateMetabolism()
    }
    fun updateBmi() {
        val weight = UserManager.getWeight()
        val height = UserManager.getHeight()
        if(height == null || weight == null){
            _bmi.value = 0f
            Log.i("TEST", "$weight")
        }else {
            _bmi.value = weight.div(convertCmToInch(height).pow(2)) * 703
        }
    }

    fun updateBodyFat() {
        val weight = UserManager.getWeight()
        val height = UserManager.getHeight()
        val dob = UserManager.getDOB()
        val gender = UserManager.getGender()
        if(height == null || weight == null || dob == null) {
            _bodyFat.value = 0f
        }else {
            val age = calculateAge(dob)
            if(gender == Gender.UNKNOWN || gender == Gender.MALE) {
                _bodyFat.value = ((1.20f * _bmi.value) + (0.23f * age) - 16.2f)
            }else {
                _bodyFat.value = ((1.20f * _bmi.value) + (0.23f * age) - 5.4f)
            }
        }
    }

    fun updateMetabolism() {
        val weight = UserManager.getWeight()
        val height = UserManager.getHeight()
        val dob = UserManager.getDOB()
        val gender = UserManager.getGender()
        if(height == null || weight == null || dob == null) {
            _metabolism.value = 0f
        }else {
            val age = calculateAge(dob)
            if(gender == Gender.UNKNOWN || gender == Gender.MALE) {
                _metabolism.value = 88.362f + (13.397f * convertLbsToKg(weight)) + (4.799f * convertCmToInch(height)) - (5.677f * age)
            }else {
                _metabolism.value = 447.593f + (9.247f * convertLbsToKg(weight)) + (3.098f * convertCmToInch(height)) - (4.330f * age)
            }
        }
    }

    private fun calculateAge(dobString: String): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dob = sdf.parse(dobString)
        val cal = Calendar.getInstance()

        return if (dob != null) {
            val now = cal.timeInMillis
            cal.time = dob
            val dobMillis = cal.timeInMillis

            val ageMillis = now - dobMillis
            val age = (ageMillis / (1000L * 60 * 60 * 24 * 365)).toInt()
            age
        } else {
            0
        }
    }

    private fun convertCmToInch(height: Float): Float {
        return height * 0.393701f
    }

    private fun convertLbsToKg(weight: Float): Float {
        return weight * 0.453592f
    }
}
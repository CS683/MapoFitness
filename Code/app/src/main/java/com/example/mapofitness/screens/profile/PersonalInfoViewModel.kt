package com.example.mapofitness.screens.profile

import androidx.lifecycle.ViewModel
import com.example.mapofitness.data.local.entity.Gender
import com.example.mapofitness.data.local.entity.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PersonalInfoViewModel : ViewModel() {
    private val _gender = MutableStateFlow<Gender>(Gender.UNKNOWN)
    val gender: StateFlow<Gender> = _gender

    private val _dob = MutableStateFlow<String>("")
    val dob: StateFlow<String> = _dob

    private val _height = MutableStateFlow<Float>(0f)
    val height: StateFlow<Float> = _height

    private val _weight = MutableStateFlow<Float>(0f)
    val weight: StateFlow<Float> = _weight

    init {
        fetchGender()
        if(UserManager.getDOB() != null) {
            fetchDOB()
        }
        if(UserManager.getHeight() != null) {
            fetchHeight()
        }
        if(UserManager.getWeight() != null) {
            fetchWeight()
        }
    }

    private fun fetchGender() {
        _gender.value = UserManager.getGender()
    }

    private fun fetchDOB() {
        _dob.value = UserManager.getDOB()!!
    }

    private fun fetchHeight() {
        _height.value = UserManager.getHeight()!!
    }

    private fun fetchWeight() {
        _weight.value = UserManager.getWeight()!!
    }

    fun updateGender(newGender: Gender) {
        _gender.value = newGender
    }

    fun updateDOB(newDOB: String) {
        _dob.value = newDOB
    }

    fun updateHeight(newHeight: Float) {
        _height.value = newHeight
    }

    fun updateWeight(newWeight: Float) {
        _weight.value = newWeight
    }
}
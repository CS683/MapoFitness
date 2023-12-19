package com.example.mapofitness.screens.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapofitness.data.local.entity.Activity
import com.example.mapofitness.data.local.entity.UserManager.fetchActivity

class ActivityViewModel: ViewModel() {
    private val _activityList = MutableLiveData<List<Activity>>()
    val activityList: LiveData<List<Activity>> = _activityList

    init {
        loadList()
    }

    private fun loadList() {
        fetchActivity{ activities ->
            _activityList.value = activities
        }
    }
}


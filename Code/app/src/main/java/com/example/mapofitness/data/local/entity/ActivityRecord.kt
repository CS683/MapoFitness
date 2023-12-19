package com.example.mapofitness.data.local.entity

data class ActivityRecord(
    val date: String,
    val activity: String,
    val time: Int,
    val calorieBurned: Float
){
    constructor() : this("","",0,0f)
}
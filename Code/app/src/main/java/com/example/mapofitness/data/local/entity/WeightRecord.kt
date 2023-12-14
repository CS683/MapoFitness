package com.example.mapofitness.data.local.entity

data class WeightRecord(
    val date: String,
    val weight: Float
){
    constructor(): this("0", 0f)
}
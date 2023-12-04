package com.example.mapofitness.screens.login

import com.example.mapofitness.data.local.entity.User

data class SignInResult(
    val data: User?,
    val errorMessage: String?
)

package com.bangkit.farmtrade.data.remote.response

data class ProfileResponse(

    val id: String,
    val name: String,
    val imageUrl: String,
    val email: String,
    val contact: String,
    val password: String
)
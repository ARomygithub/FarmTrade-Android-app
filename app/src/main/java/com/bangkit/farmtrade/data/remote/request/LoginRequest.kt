package com.bangkit.farmtrade.data.remote.request

data class LoginRequest(
    val email: String,
    val password: String,
) {
}
package com.bangkit.farmtrade.data.remote.retrofit

import com.bangkit.farmtrade.data.remote.request.LoginRequest
import com.bangkit.farmtrade.data.remote.request.RegisterRequest
import com.bangkit.farmtrade.data.remote.response.LoginResponse
import com.bangkit.farmtrade.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}
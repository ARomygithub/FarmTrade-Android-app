package com.bangkit.farmtrade.data.remote.retrofit

import com.bangkit.farmtrade.data.remote.request.ForecastRequest
import com.bangkit.farmtrade.data.remote.request.LoginRequest
import com.bangkit.farmtrade.data.remote.request.RegisterRequest
import com.bangkit.farmtrade.data.remote.response.ForecastResponse
import com.bangkit.farmtrade.data.remote.response.LoginResponse
import com.bangkit.farmtrade.data.remote.response.ProductResponse
import com.bangkit.farmtrade.data.remote.response.ProfileResponse
import com.bangkit.farmtrade.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET("product")
    suspend fun getProduct(): ProductResponse

    @GET("profile/{id}")
    suspend fun getProfile(@Path("id") id: Int): ProfileResponse

    @PUT("profile/{id}")
    suspend fun updateProfile(@Path("id") id: Int, @Body request: ProfileResponse): ProfileResponse

    @GET("predict")
    suspend fun getForecast(@Body request: ForecastRequest): ForecastResponse
}
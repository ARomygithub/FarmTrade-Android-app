package com.bangkit.farmtrade.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.farmtrade.data.local.pref.PreferenceManager
import com.bangkit.farmtrade.data.remote.request.LoginRequest
import com.bangkit.farmtrade.data.remote.response.LoginResponse
import com.bangkit.farmtrade.data.remote.retrofit.ApiService
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val pref: PreferenceManager
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading
    private val _response = MutableLiveData<LoginResponse>()
    val response : LiveData<LoginResponse> = _response

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val responseApi = apiService.login(LoginRequest(
                    email = email,
                    password = password
                ))
                _response.value = responseApi
                if(!responseApi.error) {
                    setToken(responseApi.loginResult.token)
                }
            } catch(e: HttpException) {
                val errorBody = Gson().fromJson(e.response()?.errorBody()?.charStream(), LoginResponse::class.java)
                _response.value = errorBody
            }
            _isLoading.value = false
        }
    }

    private fun setToken(token: String) {
        viewModelScope.launch {
            pref.setToken(token)
        }
    }

    fun setIdFromUser(user: FirebaseUser) {
        viewModelScope.launch {
            // do something
//            apiService.
//            pref.setId(id)
        }
    }
}
package com.bangkit.farmtrade.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.farmtrade.data.remote.request.RegisterRequest
import com.bangkit.farmtrade.data.remote.response.RegisterResponse
import com.bangkit.farmtrade.data.remote.retrofit.ApiService
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading
    private val _response = MutableLiveData<RegisterResponse>()
    val response : LiveData<RegisterResponse> = _response

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val responseApi = apiService.register(RegisterRequest(
                    name = name,
                    email = email,
                    password = password
                ))
                _response.value = responseApi
            } catch(e: HttpException) {
                val errorBody = Gson().fromJson(e.response()?.errorBody()?.charStream(), RegisterResponse::class.java)
                _response.value = errorBody
            }
            _isLoading.value = false
        }
    }

    fun createUser(user: FirebaseUser?) {
        //do something
    }
}
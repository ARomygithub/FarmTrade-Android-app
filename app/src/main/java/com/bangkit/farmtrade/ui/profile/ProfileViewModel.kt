package com.bangkit.farmtrade.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.farmtrade.data.local.pref.PreferenceManager
import com.bangkit.farmtrade.data.remote.response.ProfileResponse
import com.bangkit.farmtrade.data.remote.response.RegisterResponse
import com.bangkit.farmtrade.data.remote.retrofit.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val pref: PreferenceManager
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isEditable = MutableLiveData<Boolean>(false)
    val isEditable: LiveData<Boolean> = _isEditable
    private val _profileResponse = MutableLiveData<ProfileResponse>()
    val profileResponse: LiveData<ProfileResponse> = _profileResponse
    private val _saveResponse = MutableLiveData<RegisterResponse>()
    val saveResponse: LiveData<RegisterResponse> = _saveResponse

    fun editProfile() {
        _isEditable.value = true
    }

    fun saveProfile(name: String, email: String, contact: String) {
        _isLoading.value = true
        var id: String
        runBlocking { id = pref.getId().first() }
        // do something
        _saveResponse.value = RegisterResponse(false, "Success")
        _isEditable.value = false
        _isLoading.value = false
    }

    fun cancelSave() {
        _isEditable.value = false
    }

    fun getProfile() {
        _isLoading.value = true
        // do something
//        _profileResponse.value =  ProfileResponse(
//            "1",
//            "Richard",
//            "https://avatars.githubusercontent.com/u/24701601?v=4",
//            "tes123@gmail.com",
//            "08123456789",
//            "123456"
//        )
        _isLoading.value = false
    }

    fun logout() {
        viewModelScope.launch {
            pref.clearSession()
        }
    }
}

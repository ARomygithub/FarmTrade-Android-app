package com.bangkit.farmtrade

import androidx.lifecycle.ViewModel
import com.bangkit.farmtrade.data.local.pref.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pref : PreferenceManager
) : ViewModel() {
    fun isLogin(): Boolean {
        var token: String
        runBlocking { token = pref.getToken().first() }
        return token.isNotEmpty()
    }
}
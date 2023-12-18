package com.bangkit.farmtrade.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.farmtrade.MainActivity
import com.bangkit.farmtrade.databinding.ActivityLoginBinding
import com.bangkit.farmtrade.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {
    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        supportActionBar?.setLogo(R.drawable.tradefarm_48)
        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.isLoading.observe(this) {isLoading ->
            showLoading(isLoading)
        }
        binding.btnLogin.setOnClickListener {
            with(binding) {
                loginViewModel.login(
                    edLoginEmail.text.toString(),
                    edLoginPassword.text.toString()
                )
            }
            loginViewModel.response.value?.let { response ->
                AlertDialog.Builder(this).apply {
                    setTitle(if (response.error) "Login Error" else "Login Success")
                    setMessage(response.message)
                    setPositiveButton("OK") { _, _ ->
                        if (!response.error) {
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                    }
                    create()
                    show()
                }
            }
        }
        binding.needAccountText.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
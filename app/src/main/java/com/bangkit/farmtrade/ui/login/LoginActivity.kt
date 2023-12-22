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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {
    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
//        supportActionBar?.setLogo(R.drawable.tradefarm_48)
        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
        val loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.isLoading.observe(this) {isLoading ->
            showLoading(isLoading)
        }
        binding.btnLogin.setOnClickListener {
            auth.signInWithEmailAndPassword(binding.edLoginEmail.text.toString(), binding.edLoginPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        loginViewModel.setIdFromUser(auth.currentUser!!)
                        startActivity(intent)
                        finish()
                    } else {
                        AlertDialog.Builder(this).apply {
                            setTitle("Login Error")
                            setMessage(task.exception?.message)
                            setPositiveButton("OK") { _, _ ->
                                // Do nothing
                            }
                            create()
                            show()
                        }
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
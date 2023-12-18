package com.bangkit.farmtrade.ui.signup

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.farmtrade.databinding.ActivitySignupBinding
import com.bangkit.farmtrade.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity: AppCompatActivity() {
    private var _binding: ActivitySignupBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val signupViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        signupViewModel.isLoading.observe(this) {isLoading ->
            showLoading(isLoading)
        }
        setNameListener()
        setEmailListener()
        setPasswordListener()
        binding.btnRegister.setOnClickListener {
            with(binding) {
                signupViewModel.signUp(
                    edRegisName.text.toString(),
                    edRegisEmail.text.toString(),
                    edRegisPassword.text.toString()
                )
            }
            signupViewModel.response.value?.let { response ->
                AlertDialog.Builder(this).apply {
                    setTitle(if (response.error) "Register Error" else "Register Success")
                    setMessage(response.message)
                    setPositiveButton("OK") { _, _ ->
                        if (!response.error) {
                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
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
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun checkFieldValid(): Boolean {
        return binding.edRegisName.error==null &&
                binding.edRegisEmail.error==null &&
                binding.edRegisPassword.error==null &&
                binding.edRegisConfirmPassword.error==null
    }

    private fun setNameListener() {
        binding.edRegisName.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                with(binding) {
                    edRegisName.error = if(edRegisName.text.toString().isEmpty()) "Nama wajib diisi" else null
                    btnRegister.isEnabled = checkFieldValid()
                }
            }

            override fun afterTextChanged(s: android.text.Editable?) {
                // Do nothing
            }
        })
    }

    private fun setEmailListener() {
        binding.edRegisEmail.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                with(binding) {
                    edRegisEmail.error = if(edRegisEmail.text.toString().isEmpty()) "Email wajib diisi" else null
                    btnRegister.isEnabled = checkFieldValid()
                }
            }

            override fun afterTextChanged(s: android.text.Editable?) {
                // Do nothing
            }
        })
    }

    private fun setPasswordListener() {
        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                with(binding) {
                    edRegisPassword.error = if(edRegisPassword.text.toString().isEmpty()) "Password wajib diisi" else null
                    edRegisConfirmPassword.error = if(edRegisPassword.text.toString().equals(edRegisConfirmPassword.text.toString())) null else "Password tidak sama"
                    btnRegister.isEnabled = checkFieldValid()
                }
            }

            override fun afterTextChanged(s: android.text.Editable?) {
                // Do nothing
            }
        }
        binding.edRegisPassword.addTextChangedListener(textWatcher)
        binding.edRegisConfirmPassword.addTextChangedListener(textWatcher)
    }
}
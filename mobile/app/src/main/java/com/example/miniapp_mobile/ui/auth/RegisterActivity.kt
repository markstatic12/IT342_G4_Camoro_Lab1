package com.example.miniapp_mobile.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.miniapp_mobile.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        // Text field listeners
        binding.firstNameInput.doAfterTextChanged { text ->
            viewModel.updateFirstName(text.toString())
        }
        
        binding.lastNameInput.doAfterTextChanged { text ->
            viewModel.updateLastName(text.toString())
        }
        
        binding.emailInput.doAfterTextChanged { text ->
            viewModel.updateEmail(text.toString())
        }
        
        binding.passwordInput.doAfterTextChanged { text ->
            viewModel.updatePassword(text.toString())
        }
        
        binding.confirmPasswordInput.doAfterTextChanged { text ->
            viewModel.updateConfirmPassword(text.toString())
        }
        
        // Register button click
        binding.registerButton.setOnClickListener {
            viewModel.register()
        }
        
        // Login link click
        binding.loginLink.setOnClickListener {
            // TODO: Navigate to login screen
            Toast.makeText(this, "Login screen coming soon", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun observeViewModel() {
        viewModel.registerState.observe(this) { state ->
            when (state) {
                is RegisterState.Idle -> {
                    hideLoading()
                    hideError()
                }
                is RegisterState.Loading -> {
                    showLoading()
                    hideError()
                }
                is RegisterState.Success -> {
                    hideLoading()
                    hideError()
                    showSuccess(state.message)
                    clearForm()
                }
                is RegisterState.Error -> {
                    hideLoading()
                    showError(state.message)
                }
            }
        }
    }
    
    private fun showLoading() {
        binding.loadingIndicator.visibility = View.VISIBLE
        binding.registerButton.isEnabled = false
        binding.firstNameInput.isEnabled = false
        binding.lastNameInput.isEnabled = false
        binding.emailInput.isEnabled = false
        binding.passwordInput.isEnabled = false
        binding.confirmPasswordInput.isEnabled = false
    }
    
    private fun hideLoading() {
        binding.loadingIndicator.visibility = View.GONE
        binding.registerButton.isEnabled = true
        binding.firstNameInput.isEnabled = true
        binding.lastNameInput.isEnabled = true
        binding.emailInput.isEnabled = true
        binding.passwordInput.isEnabled = true
        binding.confirmPasswordInput.isEnabled = true
    }
    
    private fun showError(message: String) {
        binding.errorCard.visibility = View.VISIBLE
        binding.errorText.text = message
    }
    
    private fun hideError() {
        binding.errorCard.visibility = View.GONE
    }
    
    private fun showSuccess(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(getColor(com.example.miniapp_mobile.R.color.success))
            .setTextColor(getColor(com.example.miniapp_mobile.R.color.white))
            .show()
    }
    
    private fun clearForm() {
        binding.firstNameInput.text?.clear()
        binding.lastNameInput.text?.clear()
        binding.emailInput.text?.clear()
        binding.passwordInput.text?.clear()
        binding.confirmPasswordInput.text?.clear()
    }
}

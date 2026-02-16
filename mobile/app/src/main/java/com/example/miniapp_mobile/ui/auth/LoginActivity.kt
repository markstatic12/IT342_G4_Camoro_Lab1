package com.example.miniapp_mobile.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.miniapp_mobile.MainActivity
import com.example.miniapp_mobile.data.repository.AuthRepository
import com.example.miniapp_mobile.databinding.ActivityLoginBinding
import com.example.miniapp_mobile.utils.SessionManager
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager
    
    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(AuthRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize session manager
        sessionManager = SessionManager(this)
        
        // Check if already logged in
        if (sessionManager.isLoggedIn()) {
            navigateToMain()
            return
        }
        
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        // Text field listeners
        binding.emailInput.doAfterTextChanged { text ->
            viewModel.updateEmail(text.toString())
            hideError()
        }

        binding.passwordInput.doAfterTextChanged { text ->
            viewModel.updatePassword(text.toString())
            hideError()
        }

        // Login button click
        binding.loginButton.setOnClickListener {
            viewModel.login()
        }
        
        // Register link click
        binding.registerLink.setOnClickListener {
            navigateToRegister()
        }
        
        // Forgot password link click
        binding.forgotPasswordLink.setOnClickListener {
            Snackbar.make(
                binding.root,
                "Password reset feature coming soon",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun observeViewModel() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is LoginState.Idle -> {
                    hideLoading()
                    hideError()
                }
                is LoginState.Loading -> {
                    showLoading()
                    hideError()
                }
                is LoginState.Success -> {
                    hideLoading()
                    hideError()
                    handleLoginSuccess(state.token, state.email)
                }
                is LoginState.Error -> {
                    hideLoading()
                    showError(state.message)
                }
            }
        }
    }
    
    private fun handleLoginSuccess(token: String, email: String) {
        // Save session
        sessionManager.saveAuthToken(token)
        sessionManager.saveUserEmail(email)
        
        // Show success message
        Snackbar.make(
            binding.root,
            "Welcome back!",
            Snackbar.LENGTH_SHORT
        ).show()
        
        // Navigate to main screen
        navigateToMain()
    }

    private fun showLoading() {
        binding.loadingIndicator.visibility = View.VISIBLE
        binding.loginButton.isEnabled = false
        binding.emailInput.isEnabled = false
        binding.passwordInput.isEnabled = false
        binding.registerLink.isEnabled = false
        binding.forgotPasswordLink.isEnabled = false
    }

    private fun hideLoading() {
        binding.loadingIndicator.visibility = View.GONE
        binding.loginButton.isEnabled = true
        binding.emailInput.isEnabled = true
        binding.passwordInput.isEnabled = true
        binding.registerLink.isEnabled = true
        binding.forgotPasswordLink.isEnabled = true
    }
    
    private fun showError(message: String) {
        binding.errorCard.visibility = View.VISIBLE
        binding.errorText.text = message
    }
    
    private fun hideError() {
        binding.errorCard.visibility = View.GONE
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    
    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
    
    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetState()
    }
}

package com.example.miniapp_mobile.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.miniapp_mobile.R
import com.example.miniapp_mobile.data.SessionManager
import com.example.miniapp_mobile.databinding.FragmentLoginBinding
import com.example.miniapp_mobile.viewmodel.AuthUiState
import com.example.miniapp_mobile.viewmodel.AuthViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        // Auto-redirect if already logged in
        if (sessionManager.isLoggedIn()) {
            navigateToDashboard()
            return
        }

        authViewModel.resetLoginState()
        setupTabSwitcher()
        setupClickListeners()
        observeLoginState()

        // Pre-fill email if navigated from Register
        val prefillEmail = arguments?.getString("prefillEmail", "") ?: ""
        if (prefillEmail.isNotEmpty()) {
            binding.etEmail.setText(prefillEmail)
            binding.etPassword.requestFocus()
        }
    }

    private fun setupTabSwitcher() {
        // Login tab is already visually selected in XML
        binding.tabRegister.setOnClickListener {
            authViewModel.resetLoginState()
            findNavController().navigate(R.id.action_login_to_register)
        }
    }

    private fun setupClickListeners() {
        binding.btnSignIn.setOnClickListener {
            if (validateInputs()) {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                authViewModel.login(email, password)
            }
        }
    }

    private fun validateInputs(): Boolean {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        hideError()

        return when {
            email.isEmpty() -> {
                showError("Email is required")
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showError("Please enter a valid email address")
                false
            }
            password.isEmpty() -> {
                showError("Password is required")
                false
            }
            password.length < 6 -> {
                showError("Password must be at least 6 characters")
                false
            }
            else -> true
        }
    }

    private fun observeLoginState() {
        authViewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthUiState.Idle -> {
                    setLoading(false)
                    hideError()
                }
                is AuthUiState.Loading -> {
                    setLoading(true)
                    hideError()
                }
                is AuthUiState.Success -> {
                    setLoading(false)
                    sessionManager.saveToken(state.data.token)
                    sessionManager.saveUser(state.data.user)
                    authViewModel.resetLoginState()
                    navigateToDashboard()
                }
                is AuthUiState.Error -> {
                    setLoading(false)
                    showError(state.message)
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnSignIn.isEnabled = !isLoading
        binding.etEmail.isEnabled = !isLoading
        binding.etPassword.isEnabled = !isLoading
    }

    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.tvError.visibility = View.GONE
    }

    private fun navigateToDashboard() {
        findNavController().navigate(R.id.action_login_to_dashboard)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
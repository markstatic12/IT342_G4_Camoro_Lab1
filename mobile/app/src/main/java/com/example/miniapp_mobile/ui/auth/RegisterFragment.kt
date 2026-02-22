package com.example.miniapp_mobile.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.miniapp_mobile.R
import com.example.miniapp_mobile.databinding.FragmentRegisterBinding
import com.example.miniapp_mobile.viewmodel.AuthUiState
import com.example.miniapp_mobile.viewmodel.AuthViewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()

    private var registeredEmail = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.resetRegisterState()

        setupTabSwitcher()
        setupClickListeners()
        observeRegisterState()
    }

    private fun setupTabSwitcher() {
        // Register tab is already visually selected in XML
        binding.tabLogin.setOnClickListener {
            authViewModel.resetRegisterState()
            findNavController().popBackStack()
        }
    }

    private fun setupClickListeners() {
        binding.btnRegister.setOnClickListener {
            if (validateInputs()) {
                registeredEmail = binding.etEmail.text.toString().trim()
                authViewModel.register(
                    firstName = binding.etFirstName.text.toString().trim(),
                    lastName = binding.etLastName.text.toString().trim(),
                    email = registeredEmail,
                    password = binding.etPassword.text.toString().trim()
                )
            }
        }
    }

    private fun validateInputs(): Boolean {
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etLastName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        hideError()

        return when {
            firstName.isEmpty() -> {
                showError("First name is required")
                false
            }
            firstName.length < 2 -> {
                showError("First name must be at least 2 characters")
                false
            }
            lastName.isEmpty() -> {
                showError("Last name is required")
                false
            }
            lastName.length < 2 -> {
                showError("Last name must be at least 2 characters")
                false
            }
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
            confirmPassword.isEmpty() -> {
                showError("Please confirm your password")
                false
            }
            password != confirmPassword -> {
                showError("Passwords do not match")
                false
            }
            else -> true
        }
    }

    private fun observeRegisterState() {
        authViewModel.registerState.observe(viewLifecycleOwner) { state ->
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
                    authViewModel.resetRegisterState()
                    Toast.makeText(
                        requireContext(),
                        "Account created! Please log in.",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Navigate to Login and pass email so it pre-fills
                    val bundle = bundleOf("prefillEmail" to registeredEmail)
                    findNavController().navigate(R.id.action_register_to_login, bundle)
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
        binding.btnRegister.isEnabled = !isLoading
        binding.etFirstName.isEnabled = !isLoading
        binding.etLastName.isEnabled = !isLoading
        binding.etEmail.isEnabled = !isLoading
        binding.etPassword.isEnabled = !isLoading
        binding.etConfirmPassword.isEnabled = !isLoading
    }

    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.tvError.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.miniapp_mobile.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.miniapp_mobile.R
import com.example.miniapp_mobile.data.SessionManager
import com.example.miniapp_mobile.databinding.FragmentDashboardBinding
import com.example.miniapp_mobile.viewmodel.AuthUiState
import com.example.miniapp_mobile.viewmodel.AuthViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        setupBottomNav()
        loadProfile()
        observeProfile()
        blockBackNavigation()
    }

    private fun setupBottomNav() {
        binding.bottomNav.selectedItemId = R.id.nav_dashboard
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> true
                R.id.nav_profile -> {
                    findNavController().navigate(R.id.action_dashboard_to_profile)
                    true
                }
                else -> false
            }
        }
    }

    private fun loadProfile() {
        // Show cached data immediately while fetching fresh data
        sessionManager.getUser()?.let { cached ->
            updateUI(cached.firstName, cached.lastName, cached.email)
        }
        // Fetch fresh profile from server
        val token = sessionManager.getToken()
        if (token != null) {
            authViewModel.loadProfile(token)
        }
    }

    private fun observeProfile() {
        authViewModel.profileState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthUiState.Success -> {
                    val user = state.data
                    sessionManager.saveUser(user)
                    updateUI(user.firstName, user.lastName, user.email)
                }
                is AuthUiState.Error -> {
                    // Already showing cached data — nothing more to do
                }
                else -> { /* Loading or Idle */ }
            }
        }
    }

    private fun updateUI(firstName: String, lastName: String, email: String) {
        binding.tvWelcome.text = "Welcome back, $firstName."
        binding.tvEmail.text = email
        binding.tvFirstName.text = firstName
        binding.tvLastName.text = lastName
    }

    private fun blockBackNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Prevent back-stack navigation out of dashboard
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
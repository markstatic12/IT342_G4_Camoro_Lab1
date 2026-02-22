package com.example.miniapp_mobile.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.miniapp_mobile.R
import com.example.miniapp_mobile.data.SessionManager
import com.example.miniapp_mobile.databinding.FragmentProfileBinding
import com.example.miniapp_mobile.viewmodel.AuthUiState
import com.example.miniapp_mobile.viewmodel.AuthViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        setupBottomNav()
        loadCachedProfile()
        observeProfile()
        setupLogout()
        handleBackNavigation()
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNav.menu.findItem(R.id.nav_profile)?.isChecked = true
    }

    private fun setupBottomNav() {
        binding.bottomNav.selectedItemId = R.id.nav_profile
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    // Dashboard is still in back stack — just pop back to it
                    findNavController().popBackStack()
                    true
                }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }

    private fun loadCachedProfile() {
        sessionManager.getUser()?.let { user ->
            updateUI(
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                createdAt = user.createdAt
            )
        }
    }

    private fun observeProfile() {
        authViewModel.profileState.observe(viewLifecycleOwner) { state ->
            if (state is AuthUiState.Success) {
                val user = state.data
                sessionManager.saveUser(user)
                updateUI(user.firstName, user.lastName, user.email, user.createdAt)
            }
        }
    }

    private fun updateUI(
        firstName: String,
        lastName: String,
        email: String,
        createdAt: String?
    ) {
        val initials = "${firstName.firstOrNull() ?: ""}${lastName.firstOrNull() ?: ""}".uppercase()
        binding.tvAvatar.text = initials
        binding.tvFullName.text = "$firstName $lastName"
        binding.tvEmailSub.text = email
        binding.tvFirstName.text = firstName
        binding.tvLastName.text = lastName
        binding.tvEmail.text = email
        binding.tvCreatedAt.text = formatDate(createdAt)
    }

    private fun formatDate(isoDate: String?): String {
        if (isoDate == null) return "N/A"
        return try {
            val datePart = isoDate.substring(0, 10) // "yyyy-MM-dd"
            val parts = datePart.split("-")
            val year = parts[0]
            val month = parts[1].toInt()
            val day = parts[2].toInt()
            val monthNames = arrayOf("",
                "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            )
            "${monthNames[month]} $day, $year"
        } catch (e: Exception) {
            isoDate.take(10)
        }
    }

    private fun setupLogout() {
        binding.btnLogout.setOnClickListener {
            showLogoutConfirmDialog()
        }
    }

    private fun showLogoutConfirmDialog() {
        AlertDialog.Builder(requireContext(), R.style.AppDialogTheme)
            .setTitle("Log Out")
            .setMessage("Are you sure you want to log out of your account?")
            .setPositiveButton("Log Out") { _, _ -> performLogout() }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun performLogout() {
        val token = sessionManager.getToken()
        if (token != null) authViewModel.logout(token)
        sessionManager.clearSession()
        authViewModel.resetLoginState()
        authViewModel.resetProfileState()
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_profile_to_login)
    }

    private fun handleBackNavigation() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
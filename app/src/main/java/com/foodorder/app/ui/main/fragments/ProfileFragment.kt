package com.foodorder.app.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodorder.app.databinding.FragmentProfileBinding
import com.foodorder.app.ui.auth.AuthActivity

class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupClickListeners()
        loadUserData()
    }
    
    private fun setupClickListeners() {
        binding.btnLogout.setOnClickListener {
            logout()
        }
    }
    
    private fun loadUserData() {
        // Load user data from preferences or database
        val sharedPrefs = requireActivity().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        val userId = sharedPrefs.getString("user_id", "")
        
        // For demo purposes, show mock data
        binding.tvUserName.text = "John Doe"
        binding.tvUserEmail.text = "john.doe@email.com"
        binding.tvUserPhone.text = "+1234567890"
    }
    
    private fun logout() {
        val sharedPrefs = requireActivity().getSharedPreferences("user_prefs", android.content.Context.MODE_PRIVATE)
        sharedPrefs.edit()
            .putBoolean("is_logged_in", false)
            .putString("user_id", "")
            .apply()
        
        val intent = Intent(requireActivity(), AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
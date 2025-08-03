package com.foodorder.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.foodorder.app.FoodOrderApplication
import com.foodorder.app.databinding.ActivityAuthBinding
import com.foodorder.app.ui.main.MainActivity
import kotlinx.coroutines.launch

class AuthActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAuthBinding
    private lateinit var authViewModel: AuthViewModel
    private var isLoginMode = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val application = application as FoodOrderApplication
        val factory = AuthViewModelFactory(application.userRepository)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        updateUI()
        
        binding.btnSubmit.setOnClickListener {
            if (isLoginMode) {
                login()
            } else {
                register()
            }
        }
        
        binding.tvToggleMode.setOnClickListener {
            isLoginMode = !isLoginMode
            updateUI()
        }
    }
    
    private fun updateUI() {
        if (isLoginMode) {
            binding.btnSubmit.text = "Login"
            binding.tvToggleMode.text = "Don't have an account? Sign up"
            binding.etName.visibility = android.view.View.GONE
            binding.etPhone.visibility = android.view.View.GONE
        } else {
            binding.btnSubmit.text = "Sign Up"
            binding.tvToggleMode.text = "Already have an account? Login"
            binding.etName.visibility = android.view.View.VISIBLE
            binding.etPhone.visibility = android.view.View.VISIBLE
        }
    }
    
    private fun login() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        lifecycleScope.launch {
            val user = authViewModel.login(email, password)
            if (user != null) {
                saveUserSession(user.id)
                navigateToMain()
            } else {
                Toast.makeText(this@AuthActivity, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun register() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        
        lifecycleScope.launch {
            val user = authViewModel.register(name, email, phone, password)
            saveUserSession(user.id)
            navigateToMain()
        }
    }
    
    private fun saveUserSession(userId: String) {
        val sharedPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        sharedPrefs.edit()
            .putBoolean("is_logged_in", true)
            .putString("user_id", userId)
            .apply()
    }
    
    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    
    private fun observeViewModel() {
        // Observe loading state, errors, etc.
    }
}
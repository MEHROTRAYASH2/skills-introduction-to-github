package com.foodorder.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.foodorder.app.data.model.User
import com.foodorder.app.data.repository.UserRepository

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    
    suspend fun login(email: String, password: String): User? {
        return userRepository.login(email, password)
    }
    
    suspend fun register(name: String, email: String, phone: String, password: String): User {
        return userRepository.register(name, email, phone, password)
    }
}

class AuthViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
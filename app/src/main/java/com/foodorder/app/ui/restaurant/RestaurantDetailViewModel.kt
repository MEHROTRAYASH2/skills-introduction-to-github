package com.foodorder.app.ui.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.foodorder.app.data.model.CartItem
import com.foodorder.app.data.repository.CartRepository
import kotlinx.coroutines.launch

class RestaurantDetailViewModel(private val cartRepository: CartRepository) : ViewModel() {
    
    val cartItemCount: LiveData<Int> = cartRepository.getCartItemCount().asLiveData()
    val cartTotal: LiveData<Double> = cartRepository.getCartTotal().asLiveData()
    
    fun addToCart(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.addToCart(cartItem)
        }
    }
}

class RestaurantDetailViewModelFactory(private val cartRepository: CartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantDetailViewModel(cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
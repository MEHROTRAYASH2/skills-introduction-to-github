package com.foodorder.app.ui.main.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.foodorder.app.data.model.CartItem
import com.foodorder.app.data.repository.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {
    
    val cartItems: LiveData<List<CartItem>> = cartRepository.getAllCartItems().asLiveData()
    val cartTotal: LiveData<Double> = cartRepository.getCartTotal().asLiveData()
    val itemCount: LiveData<Int> = cartRepository.getCartItemCount().asLiveData()
    
    fun updateQuantity(itemId: String, newQuantity: Int) {
        viewModelScope.launch {
            cartRepository.updateQuantity(itemId, newQuantity)
        }
    }
    
    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            cartRepository.deleteCartItem(cartItem)
        }
    }
    
    fun clearCart() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }
}

class CartViewModelFactory(private val cartRepository: CartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(cartRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
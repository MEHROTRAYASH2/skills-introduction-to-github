package com.foodorder.app.data.repository

import com.foodorder.app.data.database.CartDao
import com.foodorder.app.data.model.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {
    
    fun getAllCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems()
    }
    
    suspend fun getCartItemById(itemId: String): CartItem? {
        return cartDao.getCartItemById(itemId)
    }
    
    fun getCartItemsByRestaurant(restaurantId: String): Flow<List<CartItem>> {
        return cartDao.getCartItemsByRestaurant(restaurantId)
    }
    
    suspend fun insertCartItem(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }
    
    suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem)
    }
    
    suspend fun deleteCartItem(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }
    
    suspend fun clearCart() {
        cartDao.clearCart()
    }
    
    suspend fun clearCartForRestaurant(restaurantId: String) {
        cartDao.clearCartForRestaurant(restaurantId)
    }
    
    fun getCartItemCount(): Flow<Int> {
        return cartDao.getCartItemCount()
    }
    
    fun getCartTotal(): Flow<Double> {
        return cartDao.getCartTotal()
    }
    
    suspend fun addToCart(cartItem: CartItem) {
        val existingItem = getCartItemById(cartItem.id)
        if (existingItem != null) {
            val updatedItem = existingItem.copy(
                quantity = existingItem.quantity + cartItem.quantity,
                totalPrice = (existingItem.quantity + cartItem.quantity) * cartItem.basePrice
            )
            updateCartItem(updatedItem)
        } else {
            insertCartItem(cartItem)
        }
    }
    
    suspend fun updateQuantity(itemId: String, newQuantity: Int) {
        val cartItem = getCartItemById(itemId)
        cartItem?.let {
            if (newQuantity <= 0) {
                deleteCartItem(it)
            } else {
                val updatedItem = it.copy(
                    quantity = newQuantity,
                    totalPrice = newQuantity * it.basePrice
                )
                updateCartItem(updatedItem)
            }
        }
    }
}
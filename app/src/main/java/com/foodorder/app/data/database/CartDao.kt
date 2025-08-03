package com.foodorder.app.data.database

import androidx.room.*
import com.foodorder.app.data.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItem>>
    
    @Query("SELECT * FROM cart_items WHERE id = :itemId")
    suspend fun getCartItemById(itemId: String): CartItem?
    
    @Query("SELECT * FROM cart_items WHERE restaurantId = :restaurantId")
    fun getCartItemsByRestaurant(restaurantId: String): Flow<List<CartItem>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)
    
    @Update
    suspend fun updateCartItem(cartItem: CartItem)
    
    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)
    
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
    
    @Query("DELETE FROM cart_items WHERE restaurantId = :restaurantId")
    suspend fun clearCartForRestaurant(restaurantId: String)
    
    @Query("SELECT COUNT(*) FROM cart_items")
    fun getCartItemCount(): Flow<Int>
    
    @Query("SELECT SUM(totalPrice) FROM cart_items")
    fun getCartTotal(): Flow<Double>
}
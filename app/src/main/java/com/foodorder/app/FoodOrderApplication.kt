package com.foodorder.app

import android.app.Application
import com.foodorder.app.data.database.AppDatabase
import com.foodorder.app.data.repository.CartRepository
import com.foodorder.app.data.repository.RestaurantRepository
import com.foodorder.app.data.repository.UserRepository

class FoodOrderApplication : Application() {
    
    val database by lazy { AppDatabase.getDatabase(this) }
    val userRepository by lazy { UserRepository(database.userDao()) }
    val restaurantRepository by lazy { RestaurantRepository() }
    val cartRepository by lazy { CartRepository(database.cartDao()) }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    
    companion object {
        lateinit var instance: FoodOrderApplication
            private set
    }
}
package com.foodorder.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.foodorder.app.data.model.CartItem
import com.foodorder.app.data.model.Order
import com.foodorder.app.data.model.User

@Database(
    entities = [User::class, CartItem::class, Order::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "food_order_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
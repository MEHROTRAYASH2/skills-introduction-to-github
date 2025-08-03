package com.foodorder.app.data.repository

import com.foodorder.app.data.database.UserDao
import com.foodorder.app.data.model.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {
    
    suspend fun getUserById(userId: String): User? {
        return userDao.getUserById(userId)
    }
    
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
    
    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }
    
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
    
    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
    
    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
    }
    
    suspend fun login(email: String, password: String): User? {
        // In a real app, this would validate against a server
        // For demo purposes, we'll create a mock user
        return getUserByEmail(email) ?: run {
            val mockUser = User(
                id = "user_${System.currentTimeMillis()}",
                name = "John Doe",
                email = email,
                phone = "+1234567890"
            )
            insertUser(mockUser)
            mockUser
        }
    }
    
    suspend fun register(name: String, email: String, phone: String, password: String): User {
        val user = User(
            id = "user_${System.currentTimeMillis()}",
            name = name,
            email = email,
            phone = phone
        )
        insertUser(user)
        return user
    }
}
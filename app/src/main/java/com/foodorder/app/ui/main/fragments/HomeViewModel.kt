package com.foodorder.app.ui.main.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.foodorder.app.data.model.Restaurant
import com.foodorder.app.data.repository.RestaurantRepository

class HomeViewModel(private val restaurantRepository: RestaurantRepository) : ViewModel() {
    
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> = _restaurants
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    suspend fun loadRestaurants() {
        try {
            _isLoading.value = true
            _error.value = null
            val restaurantList = restaurantRepository.getRestaurants()
            _restaurants.value = restaurantList
        } catch (e: Exception) {
            _error.value = "Failed to load restaurants: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }
    
    suspend fun searchRestaurants(query: String) {
        try {
            _isLoading.value = true
            _error.value = null
            val searchResults = restaurantRepository.searchRestaurants(query)
            _restaurants.value = searchResults
        } catch (e: Exception) {
            _error.value = "Search failed: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }
}

class HomeViewModelFactory(private val restaurantRepository: RestaurantRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(restaurantRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
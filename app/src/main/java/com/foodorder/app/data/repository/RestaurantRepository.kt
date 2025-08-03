package com.foodorder.app.data.repository

import com.foodorder.app.data.model.*
import kotlinx.coroutines.delay

class RestaurantRepository {
    
    suspend fun getRestaurants(): List<Restaurant> {
        // Simulate network delay
        delay(1000)
        return getMockRestaurants()
    }
    
    suspend fun getRestaurantById(id: String): Restaurant? {
        delay(500)
        return getMockRestaurants().find { it.id == id }
    }
    
    suspend fun searchRestaurants(query: String): List<Restaurant> {
        delay(800)
        return getMockRestaurants().filter { 
            it.name.contains(query, ignoreCase = true) ||
            it.cuisine.any { cuisine -> cuisine.contains(query, ignoreCase = true) }
        }
    }
    
    private fun getMockRestaurants(): List<Restaurant> {
        return listOf(
            Restaurant(
                id = "1",
                name = "Pizza Palace",
                description = "Authentic Italian pizzas with fresh ingredients",
                imageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=400",
                coverImageUrl = "https://images.unsplash.com/photo-1565299624946-b28f40a0ca4b?w=800",
                rating = 4.5f,
                deliveryTime = "30-45 min",
                deliveryFee = 3.99,
                minimumOrder = 15.0,
                cuisine = listOf("Italian", "Pizza"),
                address = "123 Main St, Downtown",
                latitude = 40.7128,
                longitude = -74.0060,
                isOpen = true,
                menu = getPizzaMenu()
            ),
            Restaurant(
                id = "2",
                name = "Burger Barn",
                description = "Gourmet burgers and crispy fries",
                imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400",
                coverImageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800",
                rating = 4.2f,
                deliveryTime = "25-35 min",
                deliveryFee = 2.99,
                minimumOrder = 12.0,
                cuisine = listOf("American", "Burgers"),
                address = "456 Oak Ave, Midtown",
                latitude = 40.7589,
                longitude = -73.9851,
                isOpen = true,
                menu = getBurgerMenu()
            ),
            Restaurant(
                id = "3",
                name = "Sushi Zen",
                description = "Fresh sushi and Japanese cuisine",
                imageUrl = "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=400",
                coverImageUrl = "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=800",
                rating = 4.8f,
                deliveryTime = "40-55 min",
                deliveryFee = 4.99,
                minimumOrder = 25.0,
                cuisine = listOf("Japanese", "Sushi"),
                address = "789 Pine St, Uptown",
                latitude = 40.7831,
                longitude = -73.9712,
                isOpen = true,
                menu = getSushiMenu()
            ),
            Restaurant(
                id = "4",
                name = "Taco Fiesta",
                description = "Authentic Mexican street food",
                imageUrl = "https://images.unsplash.com/photo-1565299585323-38174c4a6471?w=400",
                coverImageUrl = "https://images.unsplash.com/photo-1565299585323-38174c4a6471?w=800",
                rating = 4.3f,
                deliveryTime = "20-30 min",
                deliveryFee = 2.49,
                minimumOrder = 10.0,
                cuisine = listOf("Mexican", "Tacos"),
                address = "321 Elm St, Downtown",
                latitude = 40.7505,
                longitude = -73.9934,
                isOpen = true,
                menu = getTacoMenu()
            )
        )
    }
    
    private fun getPizzaMenu(): List<MenuCategory> {
        return listOf(
            MenuCategory(
                id = "pizza_category",
                name = "Pizzas",
                items = listOf(
                    MenuItem(
                        id = "margherita",
                        name = "Margherita Pizza",
                        description = "Fresh tomatoes, mozzarella, basil",
                        price = 16.99,
                        imageUrl = "https://images.unsplash.com/photo-1604382355076-af4b0eb60143?w=300",
                        isVegetarian = true,
                        rating = 4.6f
                    ),
                    MenuItem(
                        id = "pepperoni",
                        name = "Pepperoni Pizza",
                        description = "Classic pepperoni with mozzarella",
                        price = 18.99,
                        imageUrl = "https://images.unsplash.com/photo-1628840042765-356cda07504e?w=300",
                        isVegetarian = false,
                        rating = 4.5f
                    )
                )
            )
        )
    }
    
    private fun getBurgerMenu(): List<MenuCategory> {
        return listOf(
            MenuCategory(
                id = "burger_category",
                name = "Burgers",
                items = listOf(
                    MenuItem(
                        id = "classic_burger",
                        name = "Classic Burger",
                        description = "Beef patty, lettuce, tomato, onion",
                        price = 12.99,
                        imageUrl = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=300",
                        isVegetarian = false,
                        rating = 4.3f
                    ),
                    MenuItem(
                        id = "veggie_burger",
                        name = "Veggie Burger",
                        description = "Plant-based patty with fresh veggies",
                        price = 13.99,
                        imageUrl = "https://images.unsplash.com/photo-1525059696034-4967a729002e?w=300",
                        isVegetarian = true,
                        rating = 4.1f
                    )
                )
            )
        )
    }
    
    private fun getSushiMenu(): List<MenuCategory> {
        return listOf(
            MenuCategory(
                id = "sushi_category",
                name = "Sushi Rolls",
                items = listOf(
                    MenuItem(
                        id = "california_roll",
                        name = "California Roll",
                        description = "Crab, avocado, cucumber",
                        price = 8.99,
                        imageUrl = "https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=300",
                        isVegetarian = false,
                        rating = 4.7f
                    ),
                    MenuItem(
                        id = "salmon_roll",
                        name = "Salmon Roll",
                        description = "Fresh salmon with rice and nori",
                        price = 12.99,
                        imageUrl = "https://images.unsplash.com/photo-1617196034796-73dfa7b1fd56?w=300",
                        isVegetarian = false,
                        rating = 4.8f
                    )
                )
            )
        )
    }
    
    private fun getTacoMenu(): List<MenuCategory> {
        return listOf(
            MenuCategory(
                id = "taco_category",
                name = "Tacos",
                items = listOf(
                    MenuItem(
                        id = "beef_taco",
                        name = "Beef Taco",
                        description = "Seasoned ground beef with fresh toppings",
                        price = 3.99,
                        imageUrl = "https://images.unsplash.com/photo-1565299585323-38174c4a6471?w=300",
                        isVegetarian = false,
                        rating = 4.4f
                    ),
                    MenuItem(
                        id = "chicken_taco",
                        name = "Chicken Taco",
                        description = "Grilled chicken with cilantro and onions",
                        price = 4.49,
                        imageUrl = "https://images.unsplash.com/photo-1599974579688-8dbdd335c77f?w=300",
                        isVegetarian = false,
                        rating = 4.2f
                    )
                )
            )
        )
    }
}
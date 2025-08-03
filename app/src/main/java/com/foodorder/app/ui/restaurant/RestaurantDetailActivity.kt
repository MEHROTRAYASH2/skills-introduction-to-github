package com.foodorder.app.ui.restaurant

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.foodorder.app.FoodOrderApplication
import com.foodorder.app.R
import com.foodorder.app.data.model.CartItem
import com.foodorder.app.data.model.MenuItem
import com.foodorder.app.data.model.Restaurant
import com.foodorder.app.databinding.ActivityRestaurantDetailBinding
import com.foodorder.app.ui.restaurant.adapters.MenuAdapter
import java.util.UUID

class RestaurantDetailActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRestaurantDetailBinding
    private lateinit var restaurant: Restaurant
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var viewModel: RestaurantDetailViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        restaurant = intent.getParcelableExtra("restaurant") ?: run {
            finish()
            return
        }
        
        val application = application as FoodOrderApplication
        val factory = RestaurantDetailViewModelFactory(application.cartRepository)
        viewModel = ViewModelProvider(this, factory)[RestaurantDetailViewModel::class.java]
        
        setupUI()
        setupRecyclerView()
        observeViewModel()
    }
    
    private fun setupUI() {
        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = restaurant.name
        
        // Load restaurant images
        Glide.with(this)
            .load(restaurant.coverImageUrl)
            .placeholder(R.drawable.image_placeholder)
            .into(binding.ivRestaurantCover)
            
        Glide.with(this)
            .load(restaurant.imageUrl)
            .placeholder(R.drawable.image_placeholder)
            .into(binding.ivRestaurantLogo)
        
        // Set restaurant info
        binding.tvRestaurantName.text = restaurant.name
        binding.tvRestaurantDescription.text = restaurant.description
        binding.tvRating.text = restaurant.rating.toString()
        binding.tvDeliveryTime.text = restaurant.deliveryTime
        binding.tvDeliveryFee.text = "₹${restaurant.deliveryFee}"
        binding.tvMinimumOrder.text = "₹${restaurant.minimumOrder}"
        binding.tvCuisine.text = restaurant.cuisine.joinToString(", ")
        
        // Status
        val statusText = if (restaurant.isOpen) "Open" else "Closed"
        val statusColor = if (restaurant.isOpen) {
            getColor(android.R.color.holo_green_dark)
        } else {
            getColor(android.R.color.holo_red_dark)
        }
        binding.tvStatus.text = statusText
        binding.tvStatus.setTextColor(statusColor)
    }
    
    private fun setupRecyclerView() {
        menuAdapter = MenuAdapter { menuItem ->
            addToCart(menuItem)
        }
        
        binding.recyclerViewMenu.apply {
            layoutManager = LinearLayoutManager(this@RestaurantDetailActivity)
            adapter = menuAdapter
        }
        
        // Submit menu data
        val allMenuItems = restaurant.menu.flatMap { category ->
            category.items.map { item -> 
                item.copy(id = "${category.id}_${item.id}")
            }
        }
        menuAdapter.submitList(allMenuItems)
    }
    
    private fun addToCart(menuItem: MenuItem) {
        val cartItem = CartItem(
            id = "${restaurant.id}_${menuItem.id}_${System.currentTimeMillis()}",
            restaurantId = restaurant.id,
            restaurantName = restaurant.name,
            menuItemId = menuItem.id,
            menuItemName = menuItem.name,
            menuItemImage = menuItem.imageUrl,
            basePrice = menuItem.price,
            quantity = 1,
            customizations = emptyList(),
            totalPrice = menuItem.price
        )
        
        viewModel.addToCart(cartItem)
        Toast.makeText(this, "${menuItem.name} added to cart", Toast.LENGTH_SHORT).show()
    }
    
    private fun observeViewModel() {
        viewModel.cartItemCount.observe(this) { count ->
            // Update cart badge if needed
        }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
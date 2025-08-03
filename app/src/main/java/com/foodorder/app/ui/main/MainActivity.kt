package com.foodorder.app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.foodorder.app.R
import com.foodorder.app.databinding.ActivityMainBinding
import com.foodorder.app.ui.main.fragments.CartFragment
import com.foodorder.app.ui.main.fragments.HomeFragment
import com.foodorder.app.ui.main.fragments.OrdersFragment
import com.foodorder.app.ui.main.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupBottomNavigation()
        
        // Start with home fragment
        replaceFragment(HomeFragment())
    }
    
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_cart -> {
                    replaceFragment(CartFragment())
                    true
                }
                R.id.nav_orders -> {
                    replaceFragment(OrdersFragment())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }
    
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
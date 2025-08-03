package com.foodorder.app.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodorder.app.databinding.ActivityCheckoutBinding
import com.foodorder.app.ui.main.MainActivity

class CheckoutActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCheckoutBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        setupClickListeners()
    }
    
    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Checkout"
        
        // Mock checkout data
        binding.tvSubtotal.text = "₹16.99"
        binding.tvDeliveryFee.text = "₹3.99"
        binding.tvTax.text = "₹1.67"
        binding.tvTotal.text = "₹22.65"
    }
    
    private fun setupClickListeners() {
        binding.btnPlaceOrder.setOnClickListener {
            placeOrder()
        }
    }
    
    private fun placeOrder() {
        // Mock order placement
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show()
        
        // Navigate back to main activity
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
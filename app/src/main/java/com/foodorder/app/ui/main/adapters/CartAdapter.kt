package com.foodorder.app.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodorder.app.R
import com.foodorder.app.data.model.CartItem
import com.foodorder.app.databinding.ItemCartBinding

class CartAdapter(
    private val onQuantityChange: (CartItem, Int) -> Unit,
    private val onRemoveItem: (CartItem) -> Unit
) : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class CartViewHolder(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(cartItem: CartItem) {
            binding.apply {
                tvItemName.text = cartItem.menuItemName
                tvRestaurantName.text = cartItem.restaurantName
                tvPrice.text = "₹${String.format("%.2f", cartItem.totalPrice)}"
                tvQuantity.text = cartItem.quantity.toString()
                
                // Load item image
                Glide.with(ivItemImage.context)
                    .load(cartItem.menuItemImage)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(ivItemImage)
                
                // Quantity controls
                btnDecrease.setOnClickListener {
                    val newQuantity = cartItem.quantity - 1
                    onQuantityChange(cartItem, newQuantity)
                }
                
                btnIncrease.setOnClickListener {
                    val newQuantity = cartItem.quantity + 1
                    onQuantityChange(cartItem, newQuantity)
                }
                
                // Remove item
                btnRemove.setOnClickListener {
                    onRemoveItem(cartItem)
                }
            }
        }
    }
    
    private class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }
    }
}
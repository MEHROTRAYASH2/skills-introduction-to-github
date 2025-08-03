package com.foodorder.app.ui.restaurant.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodorder.app.R
import com.foodorder.app.data.model.MenuItem
import com.foodorder.app.databinding.ItemMenuBinding

class MenuAdapter(
    private val onAddToCart: (MenuItem) -> Unit
) : ListAdapter<MenuItem, MenuAdapter.MenuViewHolder>(MenuDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MenuViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class MenuViewHolder(
        private val binding: ItemMenuBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(menuItem: MenuItem) {
            binding.apply {
                tvItemName.text = menuItem.name
                tvItemDescription.text = menuItem.description
                tvItemPrice.text = "₹${String.format("%.2f", menuItem.price)}"
                tvRating.text = if (menuItem.rating > 0) menuItem.rating.toString() else "New"
                
                // Load item image
                Glide.with(ivItemImage.context)
                    .load(menuItem.imageUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(ivItemImage)
                
                // Vegetarian indicator
                if (menuItem.isVegetarian) {
                    ivVegIndicator.setImageResource(R.drawable.ic_veg)
                    ivVegIndicator.setColorFilter(itemView.context.getColor(R.color.vegetarianColor))
                } else {
                    ivVegIndicator.setImageResource(R.drawable.ic_non_veg)
                    ivVegIndicator.setColorFilter(itemView.context.getColor(R.color.nonVegetarianColor))
                }
                
                // Availability
                btnAddToCart.isEnabled = menuItem.isAvailable
                if (!menuItem.isAvailable) {
                    btnAddToCart.text = "Not Available"
                    btnAddToCart.alpha = 0.5f
                } else {
                    btnAddToCart.text = "Add to Cart"
                    btnAddToCart.alpha = 1.0f
                    btnAddToCart.setOnClickListener {
                        onAddToCart(menuItem)
                    }
                }
            }
        }
    }
    
    private class MenuDiffCallback : DiffUtil.ItemCallback<MenuItem>() {
        override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
            return oldItem == newItem
        }
    }
}
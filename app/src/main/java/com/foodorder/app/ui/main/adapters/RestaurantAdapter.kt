package com.foodorder.app.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodorder.app.R
import com.foodorder.app.data.model.Restaurant
import com.foodorder.app.databinding.ItemRestaurantBinding

class RestaurantAdapter(
    private val onRestaurantClick: (Restaurant) -> Unit
) : ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder>(RestaurantDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestaurantViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class RestaurantViewHolder(
        private val binding: ItemRestaurantBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(restaurant: Restaurant) {
            binding.apply {
                tvRestaurantName.text = restaurant.name
                tvRestaurantDescription.text = restaurant.description
                tvRating.text = restaurant.rating.toString()
                tvDeliveryTime.text = restaurant.deliveryTime
                tvDeliveryFee.text = "₹${restaurant.deliveryFee}"
                tvCuisine.text = restaurant.cuisine.joinToString(", ")
                
                // Load restaurant image
                Glide.with(ivRestaurantImage.context)
                    .load(restaurant.imageUrl)
                    .placeholder(R.drawable.ic_restaurant_placeholder)
                    .error(R.drawable.ic_restaurant_placeholder)
                    .into(ivRestaurantImage)
                
                // Set open/closed status
                val statusText = if (restaurant.isOpen) "Open" else "Closed"
                val statusColor = if (restaurant.isOpen) {
                    itemView.context.getColor(android.R.color.holo_green_dark)
                } else {
                    itemView.context.getColor(android.R.color.holo_red_dark)
                }
                tvStatus.text = statusText
                tvStatus.setTextColor(statusColor)
                
                root.setOnClickListener {
                    onRestaurantClick(restaurant)
                }
            }
        }
    }
    
    private class RestaurantDiffCallback : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }
    }
}
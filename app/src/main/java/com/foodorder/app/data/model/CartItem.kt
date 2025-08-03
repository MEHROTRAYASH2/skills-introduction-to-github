package com.foodorder.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey
    val id: String,
    val restaurantId: String,
    val restaurantName: String,
    val menuItemId: String,
    val menuItemName: String,
    val menuItemImage: String,
    val basePrice: Double,
    val quantity: Int,
    val customizations: List<SelectedCustomization> = emptyList(),
    val totalPrice: Double
) : Parcelable

@Parcelize
data class SelectedCustomization(
    val customizationId: String,
    val customizationName: String,
    val selectedOptions: List<CustomizationOption>
) : Parcelable
package com.foodorder.app.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val coverImageUrl: String,
    val rating: Float,
    val deliveryTime: String,
    val deliveryFee: Double,
    val minimumOrder: Double,
    val cuisine: List<String>,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val isOpen: Boolean,
    val menu: List<MenuCategory> = emptyList()
) : Parcelable

@Parcelize
data class MenuCategory(
    val id: String,
    val name: String,
    val items: List<MenuItem>
) : Parcelable

@Parcelize
data class MenuItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val isVegetarian: Boolean,
    val isAvailable: Boolean = true,
    val rating: Float = 0f,
    val customizations: List<Customization> = emptyList()
) : Parcelable

@Parcelize
data class Customization(
    val id: String,
    val name: String,
    val options: List<CustomizationOption>,
    val isRequired: Boolean = false,
    val allowMultiple: Boolean = false
) : Parcelable

@Parcelize
data class CustomizationOption(
    val id: String,
    val name: String,
    val price: Double
) : Parcelable
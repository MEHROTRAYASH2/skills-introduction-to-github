package com.foodorder.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "orders")
data class Order(
    @PrimaryKey
    val id: String,
    val userId: String,
    val restaurantId: String,
    val restaurantName: String,
    val items: List<CartItem>,
    val subtotal: Double,
    val deliveryFee: Double,
    val tax: Double,
    val total: Double,
    val status: OrderStatus,
    val orderTime: Date,
    val estimatedDeliveryTime: Date,
    val deliveryAddress: Address,
    val paymentMethod: String
) : Parcelable

enum class OrderStatus {
    PLACED,
    CONFIRMED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}
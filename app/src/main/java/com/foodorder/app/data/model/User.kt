package com.foodorder.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val profileImage: String? = null,
    val addresses: List<Address> = emptyList()
) : Parcelable

@Parcelize
data class Address(
    val id: String,
    val title: String,
    val fullAddress: String,
    val latitude: Double,
    val longitude: Double,
    val isDefault: Boolean = false
) : Parcelable
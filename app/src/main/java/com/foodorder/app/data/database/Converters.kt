package com.foodorder.app.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.foodorder.app.data.model.*
import java.util.Date

class Converters {
    
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    @TypeConverter
    fun fromAddressList(value: List<Address>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toAddressList(value: String): List<Address> {
        val listType = object : TypeToken<List<Address>>() {}.type
        return Gson().fromJson(value, listType)
    }
    
    @TypeConverter
    fun fromCartItemList(value: List<CartItem>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toCartItemList(value: String): List<CartItem> {
        val listType = object : TypeToken<List<CartItem>>() {}.type
        return Gson().fromJson(value, listType)
    }
    
    @TypeConverter
    fun fromSelectedCustomizationList(value: List<SelectedCustomization>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toSelectedCustomizationList(value: String): List<SelectedCustomization> {
        val listType = object : TypeToken<List<SelectedCustomization>>() {}.type
        return Gson().fromJson(value, listType)
    }
    
    @TypeConverter
    fun fromOrderStatus(value: OrderStatus): String {
        return value.name
    }

    @TypeConverter
    fun toOrderStatus(value: String): OrderStatus {
        return OrderStatus.valueOf(value)
    }
    
    @TypeConverter
    fun fromAddress(value: Address): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toAddress(value: String): Address {
        return Gson().fromJson(value, Address::class.java)
    }
}
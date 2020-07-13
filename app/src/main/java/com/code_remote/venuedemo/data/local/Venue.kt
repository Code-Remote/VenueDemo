package com.code_remote.venuedemo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venues")
data class Venue(
    @PrimaryKey
    val id: String,
    val name: String,
    val address: String?,
    val postalCode: String?,
    val near: String,
    val lat: Double,
    val lng: Double,
    val city: String?,
    val country: String?,
    val formattedAddress: String?
)


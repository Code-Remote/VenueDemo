package com.code_remote.venuedemo.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "venue_details")
data class VenueDetails(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String?,
    val formattedAddress: String?,
    val rating: Float?,
    val photos: List<String>,
    @Embedded val contactInfo: VenueContactInfo?
){
    data class VenueContactInfo(
        val phone: String?,
        val formattedPhone: String?,
        val twitter: String?,
        val instagram: String?,
        val facebook: String?
    )
}


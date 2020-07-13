package com.code_remote.venuedemo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VenueDao {

    @Query("SELECT * FROM venues WHERE near is :near")
    suspend fun getVenues(near: String): List<Venue>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(venues: List<Venue>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetails(venueDetails: VenueDetails)

    @Query("SELECT * FROM venue_details WHERE id is :id")
    suspend fun getVenueDetails(id: String): VenueDetails?
}
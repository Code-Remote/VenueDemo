package com.code_remote.venuedemo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.code_remote.venuedemo.data.local.Venue
import com.code_remote.venuedemo.data.local.VenueDao
import com.code_remote.venuedemo.data.local.VenueDetails
import com.code_remote.venuedemo.utilities.Converters

@Database(entities = [Venue::class, VenueDetails::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun venueDao(): VenueDao

}
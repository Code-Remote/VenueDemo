package com.code_remote.venuedemo.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VenueApiService {

    @GET("venues/search")
    suspend fun getVenueList(
        @Query("near") searchQuery: String,
        @Query("radius") radiusInMeters: Int = 1000
    ): VenueListResponseDTO

    @GET("venues/{venue_id}")
    suspend fun getVenueDetails(
        @Path("venue_id") venueId: String
    ): VenueDetailResponseDTO

}
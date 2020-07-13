package com.code_remote.venuedemo.data

import com.code_remote.venuedemo.data.local.Venue
import com.code_remote.venuedemo.data.local.VenueDao
import com.code_remote.venuedemo.data.local.VenueDetails
import com.code_remote.venuedemo.data.remote.VenueApiService
import com.code_remote.venuedemo.data.remote.isOK
import com.code_remote.venuedemo.data.remote.toDomain
import com.code_remote.venuedemo.network.Result
import retrofit2.HttpException
import javax.inject.Inject


class VenueRepository @Inject constructor(
    private val venueApiService: VenueApiService,
    private val venueDao: VenueDao
) {

    suspend fun getVenueList(near: String): Result<List<Venue>> {
        try {
            val venueListResponseDTO = venueApiService.getVenueList(near)
            return if (venueListResponseDTO.isOK()) {
                val venueList = venueListResponseDTO.toDomain(near)
                venueDao.insertAll(venueList)
                Result.handleSuccess(venueList)
            } else {
                Result.error(venueListResponseDTO.meta.errorDetail ?: "unknown error", null)
            }
        } catch (e: Exception) {
            when (e) {
                is HttpException -> e.response()?.errorBody()
            }
            val venues = venueDao.getVenues(near)
            return if (venues.isEmpty()) {
                Result.handleException(e)
            } else {
                Result.success(venues)
            }
        }
    }

    suspend fun getVenueDetails(venueId: String): Result<VenueDetails> {
        try {
            val venueDetailResponseDTO = venueApiService.getVenueDetails(venueId)
            return if (venueDetailResponseDTO.isOK()) {
                val venueDetails = venueDetailResponseDTO.toDomain()
                venueDao.insertDetails(venueDetails)
                Result.handleSuccess(venueDetails)
            } else {
                Result.error(venueDetailResponseDTO.meta.errorDetail ?: "unknown error", null)
            }
        } catch (e: Exception) {
            val venueDetails = venueDao.getVenueDetails(venueId)
            return if (venueDetails != null) {
                Result.success(venueDetails)
            } else {
                Result.handleException(e)
            }
        }
    }

}
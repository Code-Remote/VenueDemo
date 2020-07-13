package com.code_remote.venuedemo.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_remote.venuedemo.data.VenueRepository
import com.code_remote.venuedemo.data.local.Venue
import com.code_remote.venuedemo.data.local.VenueDetails
import com.code_remote.venuedemo.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val venueRepository: VenueRepository
) : ViewModel() {

    val venueList: MutableLiveData<Result<List<Venue>>> =
        MutableLiveData(Result.success(emptyList()))

    val venueDetails: MutableLiveData<Result<VenueDetails>> =
        MutableLiveData(Result.loading(null))

    fun searchForVenue(near: String) {
        viewModelScope.launch(Dispatchers.IO) {
            venueList.postValue(venueRepository.getVenueList(near))
        }
    }

    fun getVenueDetails(venueId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            venueDetails.postValue(venueRepository.getVenueDetails(venueId))
        }
    }

}
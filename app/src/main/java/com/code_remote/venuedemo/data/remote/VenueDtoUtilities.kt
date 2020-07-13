package com.code_remote.venuedemo.data.remote

import com.code_remote.venuedemo.data.local.Venue
import com.code_remote.venuedemo.data.local.VenueDetails
import java.net.HttpURLConnection

fun VenueListResponseDTO.toDomain(near: String): List<Venue> {
    val venueList = mutableListOf<Venue>()
    response?.venues?.forEach {
        venueList.add(
            Venue(
                id = it.id,
                name = it.name,
                address = it.location.address,
                postalCode = it.location.postalcode,
                near = near,
                lat = it.location.lat,
                lng = it.location.lng,
                city = it.location.city,
                country = it.location.country,
                formattedAddress = it.location.formattedAddress?.joinToString("")
            )
        )
    }
    return venueList
}

fun VenueListResponseDTO.isOK(): Boolean =
    meta.code == HttpURLConnection.HTTP_OK

fun VenueDetailResponseDTO.isOK(): Boolean =
    meta.code == HttpURLConnection.HTTP_OK

fun VenueDetailResponseDTO.toDomain() =
    VenueDetails(
        id = response.venue.id,
        name = response.venue.name,
        description = response.venue.description,
        formattedAddress = response.venue.location.formattedAddress?.joinToString(""),
        rating = response.venue.rating,
        photos = response.venue.photos.toSimplePhotoList(),
        contactInfo = VenueDetails.VenueContactInfo(
            formattedPhone = response.venue.contact?.formattedPhone,
            phone = response.venue.contact?.phone,
            instagram = response.venue.contact?.instagram,
            facebook = response.venue.contact?.facebook,
            twitter = response.venue.contact?.twitter
        )
    )

private fun PhotosDTO?.toSimplePhotoList(): List<String> {
    val simplePhotoList = mutableListOf<String>()
    val photoVenueGroup = this?.groups?.find { photoGroupDTO -> photoGroupDTO.type == "venue" }
    photoVenueGroup?.items?.forEach {
        simplePhotoList.add("${it.prefix}${it.width}x${it.height}${it.suffix}")
    }
    return simplePhotoList
}

package com.code_remote.venuedemo.data.remote

data class VenueListResponseDTO(
    val meta: Meta,
    val response: VenueInnerResponseDTO?
)

data class ErrorDTO(val meta:Meta)

data class Meta(
    val code: Int,
    val errorDetail: String?
)

data class VenueInnerResponseDTO(
    val venues: List<VenueDTO>
)

data class VenueDTO(
    val id: String,
    val name: String,
    val location: VenueLocationDTO
)

data class VenueLocationDTO(
    val address: String?,
    val lat: Double,
    val lng: Double,
    val postalcode: String?,
    val city: String?,
    val country: String?,
    val formattedAddress: List<String>?
)

data class VenueDetailResponseDTO(
    val meta: Meta,
    val response: VenueDetailInnerResponseDTO
)

data class VenueDetailInnerResponseDTO(val venue: VenueDetailsDTO)

data class VenueDetailsDTO(
    val id: String,
    val name: String,
    val description: String?,
    val location: VenueLocationDTO,
    val contact: VenueContactDTO?,
    val photos: PhotosDTO?,
    val rating: Float?

) {
    data class VenueContactDTO(
        val phone: String?,
        val formattedPhone: String?,
        val twitter: String?,
        val instagram: String?,
        val facebook: String?
    )

}

data class PhotosDTO(
    val count: Int,
    val groups: List<PhotoGroupDTO>
)

data class PhotoGroupDTO(
    val type: String?,
    val name: String?,
    val count: String?,
    val items: List<VenuePhotoDTO>
)

data class VenuePhotoDTO(
    val id: String,
    val prefix: String,
    val suffix: String,
    val width: String,
    val height: String
)

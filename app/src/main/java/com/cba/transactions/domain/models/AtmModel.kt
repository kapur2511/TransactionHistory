package com.cba.transactions.domain.models

data class AtmModel(
    val atmId: String,
    val name: String,
    val address: String,
    val location: LatLng
)

data class LatLng(
    val lat: Double,
    val lng: Double
)
package com.example.firebasep.DataModels

data class ParkingSpotsModel(
    var PID: String,
    var status: Int,
    var latitude: Double,
    var longitude: Double,
    var EV: Int,
    var handicapped: Int
)


fun validateData(parkingSpots: List<ParkingSpotsModel>): Boolean {
    // Basic validation: check if the list is not empty
    if (parkingSpots.isEmpty()) return false

    // Further validation: check each parking spot for required fields
    return parkingSpots.all { spot ->
        spot.status in 0..1 &&
                spot.handicapped in 0..1 &&
                spot.EV in 0..1

    }
}
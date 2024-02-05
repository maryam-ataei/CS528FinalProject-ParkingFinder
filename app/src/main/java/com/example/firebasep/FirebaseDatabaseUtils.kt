package com.example.firebasep
import com.example.firebasep.DataModels.ParkingSpotsModel
import com.google.firebase.database.FirebaseDatabase

fun updateFirebaseDatabase(parkingSpots: List<ParkingSpotsModel>) {
    val database = FirebaseDatabase.getInstance()
    val ref = database.getReference("parkingSlotUnity")

    parkingSpots.forEach { spot ->
        print("spot"+ spot)
        ref.child(spot.PID).setValue(spot)
    }
}
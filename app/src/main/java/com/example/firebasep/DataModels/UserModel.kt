package com.example.firebasep.DataModels

data class UserModel (
    var ev : Boolean ,
    var uName: String,
    var uEmail: String,
    var uPass: String,
    var carColor : String ,
    var carModel : String ,
    var carPlate : String ,
    var carYear : String ,
    var nname: String ,
    var Phone: String ,
    var handicapped : Boolean ,
    var type: String = "User"

)
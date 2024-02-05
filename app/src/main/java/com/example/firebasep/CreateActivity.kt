package com.example.firebasep

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.example.firebasep.DataModels.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Switch
import android.content.Intent




class CreateActivity : AppCompatActivity() {

    private lateinit var usName: EditText
    private lateinit var usPass: EditText
    private lateinit var usEmail: EditText
    private lateinit var usCarModel: EditText
    private lateinit var usCarPlate: EditText
    private lateinit var usCarColor: EditText
    private lateinit var usCarYear: EditText
    private lateinit var usNickname: EditText
    private lateinit var usPhone: EditText
    private lateinit var usHandicapped: Switch
    private lateinit var usEV: Switch

    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        usName = findViewById(R.id.usName)
        usPass = findViewById(R.id.usPass)
        usEmail = findViewById(R.id.usEmail)
        usCarModel = findViewById(R.id.usCarModel)
        usCarPlate = findViewById(R.id.usCarPlate)
        usCarColor = findViewById(R.id.usCarColor)
        usCarYear = findViewById(R.id.usCarYear)
        usNickname = findViewById(R.id.usNickname)
        usPhone = findViewById(R.id.usPhone)
        usHandicapped = findViewById(R.id.usHandicapped)
        usEV = findViewById(R.id.usEV)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")


        btnSaveData.setOnClickListener {
            saveDriversData()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }

    }

    private fun saveDriversData() {


        val Name = usName.text.toString()
        val Pass = usPass.text.toString()
        val Email = usEmail.text.toString()
        val CarColor = usCarColor.text.toString()
        val CarModel = usCarModel.text.toString()
        val CarPlate = usCarPlate.text.toString()
        val CarYear = usCarYear.text.toString()
        val Nickname = usNickname.text.toString()
        val Phone = usPhone.text.toString()
        val Handicapped = usHandicapped.isChecked
        val EV = usEV.isChecked




        if (Name.isEmpty()) {
            usName.error = "Please enter your name..."
        }
        if (Pass.isEmpty()) {
            usPass.error = "Please enter your password..."
        }

        if (Pass.length < 6) {
            usPass.error = "Password must have at least 6 characters!"

        }
        if (Email.isEmpty()) {
            usEmail.error = "Please enter the E-mail..."
        }

        if (CarModel.isEmpty()) {
            usCarModel.error = "Please enter your Car Model..."
        }


        if (CarPlate.isEmpty()) {
            usCarPlate.error = "Please enter car Plate..."
        }
        if (CarColor.isEmpty()) {
            usCarPlate.error = "Please enter car Color..."
        }

        if (Nickname.isEmpty()) {
            usNickname.error = "Please enter your name ..."
        }

        if (Phone.isEmpty()) {
            usPhone.error = "Please enter your Phone Number ..."
        }

        if (!usHandicapped.isChecked) {
            Toast.makeText(this, "Please confirm if handicapped", Toast.LENGTH_SHORT).show()
        }
        if (Email.endsWith("@wpi.edu")) {
            usEmail.error = "Login with your WPI credential!"
        }
      //  Toast.makeText(this, "We got your data and will save it soon!", Toast.LENGTH_SHORT).show()
        val uId = dbRef.push().key!!

        val driver = UserModel(EV , Name, Email, Pass , CarColor , CarModel ,CarPlate , CarYear , Nickname , Phone  , Handicapped )



        dbRef.child(uId).setValue(driver)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                    usName.text.clear()
                    usPass.text.clear()
                    usEmail.text.clear()
                    usCarModel.text.clear()
                    usCarPlate.text.clear()
                    usCarYear.text.clear()
                    usNickname.text.clear()
                    usPhone.text.clear()
                    usCarColor.text.clear()
                    usHandicapped.isChecked = false

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }

    }
}



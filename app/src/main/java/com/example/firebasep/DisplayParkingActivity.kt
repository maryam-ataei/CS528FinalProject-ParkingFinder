package com.example.firebasep
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebasep.DataModels.ParkingSpotsModel
import android.widget.GridLayout
import androidx.core.content.ContextCompat

class DisplayParkingActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ParkingAdapter: ParkingAdapter
    private var parkingList: MutableList<ParkingSpotsModel> = mutableListOf()
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_parking)

        recyclerView = findViewById(R.id.recyclerViewParkings)
        recyclerView.layoutManager = LinearLayoutManager(this)
        ParkingAdapter = ParkingAdapter(parkingList)
        recyclerView.adapter = ParkingAdapter

        var numOfParking = fetchDataFromFirebase()
        Log.d("Checkingggggg", numOfParking.toString())
        //fetchDataFromFirebase()

    }
    private fun fetchDataFromFirebase(): Int {
        Log.e("INsidee Fetch data", "Inside FtechData")
        val databaseReference = FirebaseDatabase.getInstance().getReference("parkingSlotUnity")
        var numOfParking: Int = 0
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e("DataSnapSHOTTT", "Reading Data from ")

                if (!dataSnapshot.exists()) {
                    Log.e("FirebaseConnection", "No data found in the database.")
                    return
                }

                Log.d("FirebaseConnection", "Successfully connected to Firebase database.")

                parkingList.clear()


                for (ParkingSnapshot in dataSnapshot.children) {
                    if(ParkingSnapshot.key == "Total") {
                        Log.d("checkingggg", "Snapshot Key: ${ParkingSnapshot.key}, Value: ${ParkingSnapshot.value}")
                        val numberValue = (ParkingSnapshot.value as? Map<*, *>)?.get("number") as? Long
                         numOfParking = numberValue?.toInt() ?: 0

                        Log.d("FirebaseData", "Number of Parking: $numOfParking")


                    } else {
                        val PID = ParkingSnapshot.key ?: "" // Get the key (PID)
                        val Paval = ParkingSnapshot.child("status").getValue(Int::class.java) ?: 0
                        val plong = ParkingSnapshot.child("latitude").getValue(Double::class.java) ?: 0.0
                        val plat = ParkingSnapshot.child("longitude").getValue(Double::class.java) ?: 0.0
                        val ev = ParkingSnapshot.child("EV").getValue(Int::class.java) ?: 0
                        val hc = ParkingSnapshot.child("Handicapped").getValue(Int::class.java) ?: 0


                        val parking = ParkingSpotsModel(PID, Paval, plong, plat, ev , hc)
                        parkingList.add(parking)
                    }

                }

                val linearLayout: GridLayout = findViewById(R.id.gridLayoutForButtons)

                val gridLayout: GridLayout = findViewById(R.id.gridLayoutForButtons)
                gridLayout.removeAllViews() // Clear existing views


                var currentColumn = 0
                var currentRow = 0

                val buttonGroups = mutableMapOf<Char, MutableList<Button>>()

                parkingList.forEach { parking ->
                    val button = Button(this@DisplayParkingActivity).apply {
                        text = parking.PID

                        layoutParams = GridLayout.LayoutParams().apply {
//                            columnSpec = GridLayout.spec(currentColumn)  // Set column index and span
//                            rowSpec = GridLayout.spec(currentRow)
                            width = 230 // You can replace this with the desired width
                            height = 100 // You can replace this with the desired height
                            setMargins(25, 0, 0, 0)
//
                        }

                        setTextColor(ContextCompat.getColor(this@DisplayParkingActivity, R.color.black))

                        val backgroundDrawable = when {
                            parking.status == 1 && parking.handicapped == 1 -> R.drawable.ic_handicap
                            parking.status == 1 && parking.EV == 1 -> R.drawable.ev1
                            parking.status == 1 -> R.drawable.available
                            else -> R.drawable.car_image
                        }
                        // Set background image based on availability
                        background = ContextCompat.getDrawable(
                            this@DisplayParkingActivity,
                           backgroundDrawable
                        )
                        setOnClickListener {
                            if (parking.status.toString() == "1") {
                                val intent = Intent(this@DisplayParkingActivity, NavigationView::class.java)
                                intent.putExtra("parkingId", parking.PID)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this@DisplayParkingActivity,
                                    "This location is already occupied",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    val group = parking.PID[0].toUpperCase()
                    buttonGroups.getOrPut(group) { mutableListOf() }.add(button)


//                    linearLayout.addView(button)
//                    currentColumn++
//                    if (currentColumn == 4) {
//                        currentColumn = 0
//                        currentRow++
//                    }

                }

                val groupsOrder = listOf('D', 'C', 'B', 'A')
                for (group in groupsOrder) {
                    val buttonsInGroup = buttonGroups[group] ?: continue
                    buttonsInGroup.forEach { button ->
                        linearLayout.addView(button)
                    }
                }



            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    applicationContext,
                    "Error fetching data: ${databaseError.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("DisplayParkingActivity", "Database error", databaseError.toException())
            }
        })

        return numOfParking
    }
}


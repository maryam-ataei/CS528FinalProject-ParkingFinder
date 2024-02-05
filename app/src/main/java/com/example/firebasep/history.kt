package com.example.firebasep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment

class HistoryFragment : Fragment() {


}


//class HistoryFragment : Fragment() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_home)
//
//        // Define an array of options
//        val options = arrayOf("Option 1", "Option 2", "Option 3", "Option 4")
//
//        // Create an ArrayAdapter
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//        // Set the ArrayAdapter to the Spinner
//        Spinner.adapter = adapter
//
//        // Handle Spinner item selection
//        Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
//                // Handle the selected item here
//                val selectedItem = options[position]
//                // Do something with the selected item
//            }
//
//            override fun onNothingSelected(parentView: AdapterView<*>?) {
//                // Do nothing here
//            }
//        }
//    }
//
//}
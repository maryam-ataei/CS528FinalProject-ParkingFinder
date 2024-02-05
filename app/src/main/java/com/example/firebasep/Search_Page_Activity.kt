package com.example.firebasep

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity

class SearchPageActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    private val allItems = arrayOf(
        "Salisbury Labs",
        "Morgan Hall",
        "Daniels Hall",
        "Sanford Riley Hall",
        "Fuller Apartments",
        "Founders Hall",
        "Gateway",
        "East Hall",
        "Commuter/Grad Students",
        "Stoddard A,B,C",
        "Ellsworth Apartments ",

        // Add more items as needed
    )

    private var filteredItems = allItems.toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_page) // Replace with the actual layout resource ID

        searchView = findViewById(R.id.searchView)
        listView = findViewById(R.id.listView)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, filteredItems)
        listView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterItems(newText)
                return true
            }
        })
    }

    private fun filterItems(query: String?) {
        filteredItems.clear()
        if (query.isNullOrEmpty()) {
            filteredItems.addAll(allItems)
        } else {
            for (item in allItems) {
                if (item.contains(query, ignoreCase = true)) {
                    filteredItems.add(item)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }
}

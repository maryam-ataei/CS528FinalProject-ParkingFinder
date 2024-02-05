package com.example.firebasep
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasep.R

class HistoryGrapgh : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        val spinner: Spinner = findViewById(R.id.spinnerOptions)
        val imageView1: ImageView = findViewById(R.id.imageView1)
        val imageView2: ImageView = findViewById(R.id.imageView2)
        val imageView3: ImageView = findViewById(R.id.imageView3)

        val options = arrayOf("Last Day", "Last Month")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                when (position) {
                    0 -> setImages(R.array.option1_images, imageView1, imageView2, imageView3)
                    1 -> setImages(R.array.option2_images, imageView1, imageView2, imageView3)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setImages(imageArray: Int, vararg imageViews: ImageView) {
        val images = resources.obtainTypedArray(imageArray)
        for ((index, imageView) in imageViews.withIndex()) {
            imageView.setImageResource(images.getResourceId(index, -1))
        }
        images.recycle()
    }
}

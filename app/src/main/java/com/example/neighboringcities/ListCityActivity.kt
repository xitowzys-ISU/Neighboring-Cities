package com.example.neighboringcities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class ListCityActivity : AppCompatActivity() {

    lateinit var cities: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_city)

        this.setTitle(getString(R.string.list));

        val messageTextView = findViewById<TextView>(R.id.messageTextView)
        cities = intent.getSerializableExtra("cities") as ArrayList<String>

        val spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, cities)
        spinner.adapter = adapter

        if (cities.size == 0) {
            messageTextView.text = "There are no such cities"
        } else {
            messageTextView.text = "Cities:"

        }
    }
}
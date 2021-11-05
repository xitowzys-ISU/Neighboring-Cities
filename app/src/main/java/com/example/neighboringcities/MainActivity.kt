package com.example.neighboringcities

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.google.gson.Gson
import java.io.InputStreamReader

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var gson: Gson
    private lateinit var citiesRaw: Array<City>
    private lateinit var cityDistanceEditText: EditText

    var cityName = ""
    var cityIndex = -1
    var distance = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner = findViewById<Spinner>(R.id.spinner)
        val cities_list = resources.openRawResource(R.raw.cities)
        cityDistanceEditText = findViewById(R.id.distance)

        gson = Gson()

        citiesRaw = gson.fromJson(InputStreamReader(cities_list), Cities::class.java).movies

        var city_names: ArrayList<String> = ArrayList()
        for (city in citiesRaw) {
            city_names.add(city.name)
        }

        Log.d("citiesRaw", "d: $city_names")

        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, city_names)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

    }

    fun onFindCityClick(view: android.view.View) {
        var userInput = cityDistanceEditText.text.toString().trim()

        if (userInput.isEmpty())
            userInput = "0"

        distance = userInput.toDouble()
        Log.d("cityDistanceEditText", distance.toString())

        val lat: Double = citiesRaw[cityIndex].coord.lat.toDouble()
        val lon: Double = citiesRaw[cityIndex].coord.lon.toDouble()
        val curCity: String = citiesRaw[cityIndex].name

        var suitableCites: ArrayList<String> = ArrayList()

        for (city in citiesRaw) {
            val results = FloatArray(1)
            Location.distanceBetween(
                city.coord.lat.toDouble(),
                city.coord.lon.toDouble(),
                lat,
                lon,
                results
            )

            Log.i("suitableCites", results[0].toString())
            if (city.name != curCity && results[0] <= distance) {
                suitableCites.add(city.name)
            }
        }

        Log.d("suitableCites", suitableCites.toString())


        val intent = Intent(this, ListCityActivity::class.java)
        intent.putExtra("cities", suitableCites)
        startActivity(intent)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        cityName = parent?.getItemAtPosition(position).toString()
        cityIndex = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
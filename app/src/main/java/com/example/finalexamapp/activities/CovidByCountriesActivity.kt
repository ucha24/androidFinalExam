package com.example.finalexamapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalexamapp.R
import com.example.finalexamapp.adapters.ByCountryAdapter
import com.example.finalexamapp.covidapi.Country
import com.example.finalexamapp.covidapi.Covid19CountrySummary
import com.example.finalexamapp.covidapi.RetrofitClient
import kotlinx.android.synthetic.main.activity_covid_by_countries.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CovidByCountriesActivity : AppCompatActivity() {

    private lateinit var byCountryAdapter: ByCountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid_by_countries)

        val layoutManager = LinearLayoutManager(this)
        byCountriesRecycler.layoutManager = layoutManager
        byCountryAdapter = ByCountryAdapter(ArrayList())
        byCountriesRecycler.adapter = byCountryAdapter

        getByCountry()
    }

    private fun getByCountry() {
        RetrofitClient.covid19Api.getData("summary")
            .enqueue(object : Callback<Covid19CountrySummary<List<Country>>> {
                override fun onFailure(
                    call: Call<Covid19CountrySummary<List<Country>>>,
                    t: Throwable
                ) {

                }
                override fun onResponse(
                    call: Call<Covid19CountrySummary<List<Country>>>,
                    response: Response<Covid19CountrySummary<List<Country>>>
                ) {
                    if(response.isSuccessful && response.body() != null) {
                        val byCountryList = response.body()!!.countries.sortedByDescending { it.totalConfirmed }
                        byCountryAdapter.updateByCountries(byCountryList)
                    }
                }

            })
    }
}

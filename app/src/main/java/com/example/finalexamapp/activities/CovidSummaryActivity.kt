package com.example.finalexamapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalexamapp.R
import com.example.finalexamapp.covidapi.Covid19GlobalSummary
import com.example.finalexamapp.covidapi.Global
import com.example.finalexamapp.covidapi.RetrofitClient
import kotlinx.android.synthetic.main.activity_covid_summary.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CovidSummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid_summary)

        this.getSummaryInfo()

        goToByCountries.setOnClickListener {
            startActivity(Intent(this, CovidByCountriesActivity::class.java))
        }
    }

    private fun getSummaryInfo() {
        RetrofitClient.covid19Api.getGlobal("summary")
            .enqueue(object : Callback<Covid19GlobalSummary<Global>> {
                override fun onFailure(call: Call<Covid19GlobalSummary<Global>>, t: Throwable) {

                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<Covid19GlobalSummary<Global>>,
                    response: Response<Covid19GlobalSummary<Global>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()!!.summary
                        globalNewConfirmed.text = "New Confirmed: ${responseBody.newConfirmed}"
                        globalTotalConfirmed.text = "Total Confirmed: ${responseBody.totalConfirmed}"
                        globalNewDeaths.text = "New Deaths: ${responseBody.newDeaths}"
                        globalTotalDeaths.text = "Total Deaths: ${responseBody.totalDeaths}"
                        globalNewRecovered.text = "New Recovered: ${responseBody.newRecovered}"
                        globalTotalRecovered.text = "Total Recovered: ${responseBody.totalRecovered}"
                    }
                }

            })

    }
}
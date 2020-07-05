package com.example.finalexamapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalexamapp.R
import com.example.finalexamapp.covidapi.Country
import kotlinx.android.synthetic.main.by_country_item.view.*

class ByCountryAdapter(private var countries: List<Country> )
    : RecyclerView.Adapter<ByCountryAdapter.ByCountryViewHolder>() {

    class ByCountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var country: Country

        @SuppressLint("SetTextI18n")
        fun bind(country: Country) {
            this.country = country

            itemView.byCountryName.text = "Country: ${country.country}"
            itemView.byCountryNewConfirmed.text = "New Confirmed: ${country.newConfirmed}"
            itemView.byCountryTotalConfirmed.text = "Total Confirmed: ${country.totalConfirmed}"
            itemView.byCountryNewDeaths.text = "New Deaths: ${country.newDeaths}"
            itemView.byCountryTotalDeaths.text = "Total Deaths: ${country.totalDeaths}"
            itemView.byCountryNewRecovered.text = "New Recovered: ${country.newRecovered}"
            itemView.byCountryTotalRecovered.text = "Total Recovered: ${country.totalRecovered}"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ByCountryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.by_country_item, parent, false)
        return ByCountryViewHolder(v)
    }

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: ByCountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    fun updateByCountries(countries: List<Country>) {
        this.countries = countries
        notifyDataSetChanged()
    }
}


package com.example.finalexamapp.covidapi

import com.google.gson.annotations.SerializedName


data class Global (
    @SerializedName("NewConfirmed")
    val newConfirmed: Long,

    @SerializedName("TotalConfirmed")
    val totalConfirmed: Long,

    @SerializedName("NewDeaths")
    val newDeaths: Long,

    @SerializedName("TotalDeaths")
    val totalDeaths: Long,

    @SerializedName("NewRecovered")
    val newRecovered: Long,

    @SerializedName("TotalRecovered")
    val totalRecovered: Long
)


data class Country (

    @SerializedName("Country")
    val country: String,

    @SerializedName("CountryCode")
    val countryCode: String,

    @SerializedName("Slug")
    val slug: String,

    @SerializedName("NewConfirmed")
    val newConfirmed: Long,

    @SerializedName("TotalConfirmed")
    val totalConfirmed: Long,

    @SerializedName("NewDeaths")
    val newDeaths: Long,

    @SerializedName("TotalDeaths")
    val totalDeaths: Long,

    @SerializedName("NewRecovered")
    val newRecovered: Long,

    @SerializedName("TotalRecovered")
    val totalRecovered: Long,

    @SerializedName("Date")
    val date: String
)

data class Covid19GlobalSummary<T> (

    @SerializedName("Global")
    val summary: T

)

data class Covid19CountrySummary<T> (

    @SerializedName("Countries")
    val countries: T

)
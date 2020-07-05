package com.example.finalexamapp.covidapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Covid19ApiService {

    @GET("{param}")
    fun getData(@Path("param") param: String) : Call<Covid19CountrySummary<List<Country>>>

    @GET("{param}")
    fun getGlobal(@Path("param") param: String) : Call<Covid19GlobalSummary<Global>>
}


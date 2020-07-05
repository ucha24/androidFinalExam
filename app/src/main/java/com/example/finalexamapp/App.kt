package com.example.finalexamapp

import android.app.Application
import com.example.finalexamapp.covidapi.RetrofitClient

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitClient.initClient()
    }
}


package com.example.assignment.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    private val service: ApiInterface

    companion object{
        const val BASE_URL = "https://api.nationalize.io"
    }

    init {

        val retrofit: Retrofit =  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ApiInterface::class.java)
    }

    suspend fun getDetails(query: String): PersonModel {
        return service.searchRepositories(query)
    }

}
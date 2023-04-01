package com.example.assignment.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/")
    suspend fun searchRepositories(@Query("name") name: String): PersonModel
}
package com.example.parcial2camilosilva

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET(value = "random")
    suspend fun getJokes(): Response<Jokes>

    @GET(value = "categories")
    suspend fun getJokesCategory() : Response<List<String>>

    @GET ("random")
    suspend fun getRandomByCategory(@Query("category") category: String): Response<RandomByCategory>
}
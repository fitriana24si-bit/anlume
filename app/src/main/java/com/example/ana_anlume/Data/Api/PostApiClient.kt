package com.example.ana_anlume.Data.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostApiClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val instance: PostApiService by lazy {
        retrofit.create(PostApiService::class.java)
    }
}

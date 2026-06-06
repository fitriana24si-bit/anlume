package com.example.ana_anlume.Data.Api

import com.example.ana_anlume.Data.Model.PostModel
import retrofit2.Call
import retrofit2.http.GET

interface PostApiService {
    @GET("posts")
    fun getPosts(): Call<List<PostModel>>
}

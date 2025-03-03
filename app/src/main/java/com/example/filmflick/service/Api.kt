package com.example.filmflick.service

import com.example.filmflick.model.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey :String = "c3f4ed382a7c0780c927ac058d08838f",
        @Query("page") page : Int
    ) : Call<GetMoviesResponse>


}
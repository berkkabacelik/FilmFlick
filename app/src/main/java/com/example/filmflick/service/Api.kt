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

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey : String = "c3f4ed382a7c0780c927ac058d08838f",
        @Query("page") page : Int
    ) : Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpComingMovies(
        @Query("api_key") apiKey: String="c3f4ed382a7c0780c927ac058d08838f",
        @Query("page") page: Int
    ) : Call<GetMoviesResponse>

}
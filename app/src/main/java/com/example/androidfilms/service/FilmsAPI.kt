package com.example.androidfilms.service

import com.example.androidfilms.BuildConfig
import com.example.androidfilms.models.Film
import com.example.androidfilms.models.FilmData
import com.example.androidfilms.models.SimilarFilmsData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsAPI {
    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY}&language=en-US&page=1")
    fun getTopRatedFilms() : Single<FilmData>

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}&language=en-US&page=1")
    fun getPopularFilms() : Single<FilmData>

    @GET("movie/now_playing?api_key=${BuildConfig.API_KEY}&language=en-US&page=1")
    fun getLatestFilms() : Single<FilmData>

    @GET("movie/{id}/similar?api_key=${BuildConfig.API_KEY}&language=en-US&page=1")
    fun getSimilarMovies(@Path(value = "id") id: Int) : Single<FilmData>

    @GET("search/movie?api_key=${BuildConfig.API_KEY}&language=en-US&page=1")
    fun searchMovies(@Query( "query") searchText: String) : Single<FilmData>

}
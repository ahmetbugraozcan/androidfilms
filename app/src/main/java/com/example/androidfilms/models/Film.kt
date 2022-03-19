package com.example.androidfilms.models

import com.google.gson.annotations.SerializedName

data class FilmData(
    @SerializedName("results")
    val films: List<Film>
)
data class SimilarFilmsData(
    @SerializedName("results")
    val similars: List<SimilarFilm>
)
data class SimilarFilm(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("poster_path")
    val posterPath: String?,
)

data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
data class SimiliarFilm(
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val title: String,
)

data class Film(
    @SerializedName("genres")
    val genres: ArrayList<Genre?>?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("genre_ids")
    val genreIds: ArrayList<Int?>
)
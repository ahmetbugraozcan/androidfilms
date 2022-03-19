package com.example.androidfilms

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

fun ImageView.downloadImage(url: String) {
    Picasso.get().load("https://image.tmdb.org/t/p/original${url}").resize(250, 250).into(this)
}

@BindingAdapter("android:downloadImage")
fun loadImage(view: ImageView, url: String) {
    view.downloadImage(url)
}

@BindingAdapter("app:goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

fun getGenreName(id: Int): String? {
    when (id) {
        28 -> return "Action"
        12 -> return "Adventure"
        16 -> return "Animation"
        35 -> return "Comedy"
        80 -> return "Crime"
        99 -> return "Documentary"
        18 -> return "Drama"
        10751 -> return "Family"
        14 -> return "Fantasy"
        36 -> return "History"
        27 -> return "Horror"
        10402 -> return "Music"
        9648 -> return "Mystery"
        10749 -> return "Romance"
        878 -> return "Science Fiction"
        10770 -> return "TV Movie"
        53 -> return "Thriller"
        10752 -> return "War"
        37 -> return "Western"
        else -> return null

    }
}



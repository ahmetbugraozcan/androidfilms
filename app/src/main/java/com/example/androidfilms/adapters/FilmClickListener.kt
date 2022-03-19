package com.example.androidfilms.adapters

import android.view.View
import com.example.androidfilms.models.Film

interface FilmClickListener {
    fun filmClicked(view: View, film: Film)
}
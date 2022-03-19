package com.example.androidfilms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfilms.R
import com.example.androidfilms.databinding.GenreCardBinding
import com.example.androidfilms.getGenreName
import com.example.androidfilms.models.Genre

class GenresRecyclerAdapter(val genres: ArrayList<Int?>) :
    RecyclerView.Adapter<GenresRecyclerAdapter.GenresViewHolder>() {

    class GenresViewHolder(val view: GenreCardBinding) : RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<GenreCardBinding>(
            inflater,
            R.layout.genre_card,
            parent,
            false
        )
        return GenresViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val string = genres[position]?.let { getGenreName(it) }
        string?.let {
            holder.view.genre = it
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }
}
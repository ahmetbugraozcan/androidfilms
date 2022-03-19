package com.example.androidfilms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfilms.R
import com.example.androidfilms.databinding.SimilarFilmCardBinding
import com.example.androidfilms.models.Film
import com.example.androidfilms.models.SimilarFilm
import com.example.androidfilms.views.FilmDetailsFragmentDirections
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SimilarsRecyclerAdapter(var similars: ArrayList<Film>) : RecyclerView.Adapter<SimilarsRecyclerAdapter.SimilarsViewHolder>(), FilmClickListener {

    class SimilarsViewHolder(val view: SimilarFilmCardBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<SimilarFilmCardBinding>(inflater,R.layout.similar_film_card, parent, false)
        return SimilarsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimilarsViewHolder, position: Int) {
        holder.view.similarFilm = similars.get(position)
        holder.view.listener = this

    }

    fun updateSimilars(newFilms: List<Film>) {
        similars.clear()
        similars.addAll(newFilms)
        notifyDataSetChanged()

    }
    override fun getItemCount(): Int {
        return  similars.size
    }

    override fun filmClicked(view: View, film: Film) {
        val parsedValue = Gson().toJson(film, object : TypeToken<Film>(){}.type)
        val action = FilmDetailsFragmentDirections.actionFilmDetailsFragmentSelf(parsedValue)
        Navigation.findNavController(view).navigate(action)
    }
}
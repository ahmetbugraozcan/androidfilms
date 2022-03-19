package com.example.androidfilms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfilms.R
import com.example.androidfilms.databinding.HomepageFilmCardBinding
import com.example.androidfilms.models.Film
import com.example.androidfilms.views.HomeFragmentDirections
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeRecyclerAdapter(var films: ArrayList<Film> ) : RecyclerView.Adapter<HomeRecyclerAdapter.HomeCardViewHolder>(), FilmClickListener {

    class HomeCardViewHolder(var view: HomepageFilmCardBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<HomepageFilmCardBinding>(inflater, R.layout.homepage_film_card, parent, false)
        return HomeCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeCardViewHolder, position: Int) {
        holder.view.film = films.get(position)
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return films.size
    }

    fun updateFilms(newFilms: List<Film>) {
        films.clear()
        films.addAll(newFilms)
        notifyDataSetChanged()

    }

    override fun filmClicked(view: View, film: Film) {
        val parsedValue = Gson().toJson(film, object : TypeToken<Film>(){}.type)
        val action = HomeFragmentDirections.actionHomeFragmentToFilmDetailsFragment(parsedValue)
        Navigation.findNavController(view).navigate(action)
    }
}
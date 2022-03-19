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

class LatestRecyclerAdapter(var latests: ArrayList<Film>): RecyclerView.Adapter<LatestRecyclerAdapter.LatestViewHolder>(), FilmClickListener {
    class LatestViewHolder(var view: HomepageFilmCardBinding) : RecyclerView.ViewHolder (view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<HomepageFilmCardBinding>(inflater, R.layout.homepage_film_card, parent, false)
        return  LatestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LatestViewHolder, position: Int) {
        holder.view.film = latests[position]
        holder.view.listener = this

    }

    fun updateFilms(newFilms: List<Film>) {
        latests.clear()
        latests.addAll(newFilms)
        notifyDataSetChanged()

    }
    override fun getItemCount(): Int {
        return  latests.size
    }

    override fun filmClicked(view: View, film: Film) {
        val parsedValue =  Gson().toJson(film, object : TypeToken<Film>(){}.type)
        val action = HomeFragmentDirections.actionHomeFragmentToFilmDetailsFragment(parsedValue)
        Navigation.findNavController(view).navigate(action)
    }

}
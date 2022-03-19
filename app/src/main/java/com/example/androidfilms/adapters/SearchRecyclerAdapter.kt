package com.example.androidfilms.adapters

import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfilms.MainActivity
import com.example.androidfilms.R
import com.example.androidfilms.databinding.FilmListItemBinding
import com.example.androidfilms.models.Film
import com.example.androidfilms.views.SearchFragmentDirections
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SearchRecyclerAdapter(val films: ArrayList<Film>) : RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder>(), FilmClickListener{


    class SearchViewHolder(var view: FilmListItemBinding) : RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FilmListItemBinding>(inflater, R.layout.film_list_item,parent, false)
        return  SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.view.film = films[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return films.size
    }

    fun setFilms(newFilms: List<Film>) {
        films.clear()
        films.addAll(newFilms)
    }

    override fun filmClicked(view: View, film: Film) {
        val parsedValue = Gson().toJson(film, object : TypeToken<Film>(){}.type)
        val action = SearchFragmentDirections.actionSearchFragmentToFilmDetailsFragment(parsedValue)
        val navController = Navigation.findNavController(view)

        navController.popBackStack(R.id.searchFragment, true)
        val bundle = bundleOf("film" to parsedValue)
        navController.navigate(R.id.filmDetailsFragment, bundle)


    }
}
package com.example.androidfilms.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfilms.R
import com.example.androidfilms.adapters.GenresRecyclerAdapter
import com.example.androidfilms.adapters.SimilarsRecyclerAdapter

import com.example.androidfilms.databinding.FragmentFilmDetailsBinding
import com.example.androidfilms.models.Film
import com.example.androidfilms.models.FilmData
import com.example.androidfilms.viewmodels.FilmDetailsViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class FilmDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var film: Film
    private lateinit var binding: FragmentFilmDetailsBinding
    private lateinit var genresRecyclerAdapter: GenresRecyclerAdapter
    private lateinit var similarsRecyclerAdapter: SimilarsRecyclerAdapter
    private lateinit var viewModel: FilmDetailsViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FilmDetailsViewModel::class.java)
        arguments?.let {
            film = Gson().fromJson(FilmDetailsFragmentArgs.fromBundle(it).film, object : TypeToken<Film>(){}.type)
            binding.film = film
            viewModel.getSimilarMovies(film.id)
            observeData()
            film.genreIds.let {
                genresRecyclerAdapter = GenresRecyclerAdapter(it)
                binding.genresRecycleView.layoutManager = LinearLayoutManager(view.context,RecyclerView.HORIZONTAL, false )
                binding.genresRecycleView.adapter = genresRecyclerAdapter
            }
            similarsRecyclerAdapter = SimilarsRecyclerAdapter(arrayListOf())
            binding.similarsRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
            binding.similarsRecyclerView.adapter = similarsRecyclerAdapter
        }
    }

    fun observeData(){
        viewModel.similarFilms.observe(viewLifecycleOwner) {
            similarsRecyclerAdapter.updateSimilars(it)
        }

        viewModel.similarFilmsLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.similarsProgressBar.visibility = View.VISIBLE
            } else {
                binding.similarsProgressBar.visibility = View.GONE
            }
        }
    }

}
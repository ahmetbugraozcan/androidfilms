package com.example.androidfilms.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.androidfilms.R
import com.example.androidfilms.adapters.HomeRecyclerAdapter
import com.example.androidfilms.adapters.LatestRecyclerAdapter
import com.example.androidfilms.adapters.PopularsRecyclerAdapter
import com.example.androidfilms.databinding.ActivityHomeFragmentBinding
import com.example.androidfilms.viewmodels.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityHomeFragmentBinding
    private val homeRecyclerAdapter: HomeRecyclerAdapter = HomeRecyclerAdapter(arrayListOf())
    private val popularsRecyclerAdapter : PopularsRecyclerAdapter = PopularsRecyclerAdapter(arrayListOf())
    private val latestRecyclerAdapter: LatestRecyclerAdapter = LatestRecyclerAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        if (viewModel.films.value == null) {
            viewModel.getFilms()
        }
        if(viewModel.popularFilms.value == null){
            viewModel.getPopularFilms()
        }
        if(viewModel.latestFilms.value == null){
            viewModel.getLatestFilms()
        }
        observeData()
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        binding.recyclerView.adapter = homeRecyclerAdapter

        binding.popularsRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        binding.popularsRecyclerView.adapter = popularsRecyclerAdapter

        binding.latestRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        binding.latestRecyclerView.adapter = latestRecyclerAdapter

    }

    fun observeData() {
        viewModel.films.observe(viewLifecycleOwner, Observer {
            homeRecyclerAdapter.updateFilms(it)
        })

        viewModel.popularFilms.observe(viewLifecycleOwner) {
            popularsRecyclerAdapter.updateFilms(it)
        }

        viewModel.filmsLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.popularsLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.popularsProgressBar.visibility = View.VISIBLE
            } else {
                binding.popularsProgressBar.visibility = View.GONE
            }
        })

        viewModel.latestFilms.observe(viewLifecycleOwner) {
            println("latestfilms observe : ${it}")
            latestRecyclerAdapter.updateFilms(it)
        }

        viewModel.latestLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.latestProgressBar.visibility = View.VISIBLE
            } else {
                binding.latestProgressBar.visibility = View.GONE
            }
        })



    }


}
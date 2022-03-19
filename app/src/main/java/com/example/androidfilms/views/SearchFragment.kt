package com.example.androidfilms.views

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfilms.R
import com.example.androidfilms.adapters.SearchRecyclerAdapter
import com.example.androidfilms.databinding.FragmentSearchBinding
import com.example.androidfilms.models.Film
import com.example.androidfilms.viewmodels.MainActivityViewModel
import com.example.androidfilms.viewmodels.SearchFragmentViewModel


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter : SearchRecyclerAdapter
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel::class.java)
        observeData()
        adapter = SearchRecyclerAdapter(arrayListOf<Film>())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

    }

    fun observeData(){
        viewModel.searchLoading.observe(viewLifecycleOwner) {
            if(it) {
                // progressbar true
            }
            else {
                //progressbar false
            }
        }
        viewModel.searchedFilms.observe(viewLifecycleOwner) {
            println("searchfragment observe data")
            adapter.setFilms(it)
            binding.recyclerView.post{
                adapter.notifyDataSetChanged()
            }
        }
    }

}
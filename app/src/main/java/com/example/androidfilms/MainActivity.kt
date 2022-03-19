package com.example.androidfilms

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.ViewModelProvider
import com.example.androidfilms.databinding.ActivityMainBinding
import com.example.androidfilms.viewmodels.MainActivityViewModel
import com.example.androidfilms.views.SearchResultsActivity


class MainActivity : AppCompatActivity() {
    private  lateinit var listView: ListView
    private lateinit var binding: ActivityMainBinding
    private val waitingTime = 500
    private var cntr: CountDownTimer? = null
    private lateinit var viewModel :MainActivityViewModel
    private lateinit var adapter : ArrayAdapter<String>

    val names = arrayOf("ahmet", "buğra", "özcan")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter  = ArrayAdapter(this, R.layout.film_list_item, R.id.listitemtextview,names)

        binding.listview.adapter = adapter
    }



    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        observeData()
        return super.onCreateView(name, context, attrs)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)

        menu?.let {
            val menuItem : MenuItem = menu.findItem(R.id.action_search)
            val searchView : SearchView = menuItem.actionView as SearchView
            searchView.queryHint = "Type here to search"


            menuItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                    Toast.makeText(applicationContext, "open", Toast.LENGTH_SHORT).show()
                    binding.searchLayout.visibility = View.VISIBLE
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                    Toast.makeText(applicationContext, "close", Toast.LENGTH_SHORT).show()
                    binding.searchLayout.visibility = View.GONE
                    return true
                }

            })



            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return  false
                }
                override fun onQueryTextChange(p0: String?): Boolean {
                    adapter.filter.filter(p0)
                    //filter data
                    cntr?.cancel()
                    p0?.let {
                        if(p0.length > 3){
                            cntr = object : CountDownTimer(waitingTime.toLong(), 500) {
                                override fun onTick(millisUntilFinished: Long) {
                                    Log.d("TIME", "seconds remaining: " + millisUntilFinished / 1000)
                                }

                                override fun onFinish() {
                                    //search items
                                    viewModel.searchFilms(p0)
                                    Log.d("FINISHED", "DONE")
                                }
                            }
                            cntr?.start()
                        }

                    }

                    return false
                }

            })
        }
        return true
    }

    fun observeData(){
        viewModel.searchLoading.observe(this) {
            if(it) {
                // progressbar true
            }
            else {
                //progressbar false
            }
        }
//        viewModel.searchedFilms.observe(this) {
//            // list adapter value = this
//            adapter.clear()
//            adapter.addAll(it)
//            println("films: $it")
//            adapter.notifyDataSetChanged()
//        }
    }
}
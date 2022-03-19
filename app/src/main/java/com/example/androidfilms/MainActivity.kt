package com.example.androidfilms

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.example.androidfilms.adapters.SearchRecyclerAdapter
import com.example.androidfilms.databinding.ActivityMainBinding
import com.example.androidfilms.viewmodels.MainActivityViewModel
import com.example.androidfilms.views.SearchFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val waitingTime = 500
    private var cntr: CountDownTimer? = null
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
//        val actionBar: ActionBar? = supportActionBar
//
//        actionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(binding.root)


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)


        menu?.let {
            val menuItem : MenuItem = menu.findItem(R.id.action_search)
            val searchView : SearchView = menuItem.actionView as SearchView
            searchView.queryHint = "Type here to search"

            val navController = Navigation.findNavController(
                this@MainActivity, R.id.fragmentContainerView2
            )


            navController.addOnDestinationChangedListener(object:NavController.OnDestinationChangedListener {
                override fun onDestinationChanged(
                    controller: NavController,
                    destination: NavDestination,
                    arguments: Bundle?
                ) {
                    if(menuItem.isActionViewExpanded){
                        menuItem.collapseActionView()
                    }
                }

            })


            menuItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
//                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragmentContainerView2, searchFragment).commit();
                     // replace "nav_host_fragment" with the id of your navHostFragment in activity layout

                    navController.navigate(R.id.searchFragment)
                    return true
                }

                override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {

                    if(!menuItem.isActionViewExpanded){
                        navController.popBackStack()
                    }


                    return true
                }

            })



            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return  false
                }
                override fun onQueryTextChange(p0: String?): Boolean {
//                    adapter.filter.filter(p0)
                    //filter data
                    cntr?.cancel()
                    p0?.let {
                        if(p0.length > 3){
                            cntr = object : CountDownTimer(waitingTime.toLong(), 500) {
                                override fun onTick(millisUntilFinished: Long) {
                                    Log.d("TIME", "seconds remaining: " + millisUntilFinished / 1000)
                                }
                                override fun onFinish() {
                                    viewModel.searchFilms(p0)
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



}
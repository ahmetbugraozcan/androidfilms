package com.example.androidfilms.viewmodels

import FilmsAPIService
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.androidfilms.goneUnless
import com.example.androidfilms.models.Film
import com.example.androidfilms.models.FilmData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers


class HomeViewModel : ViewModel() {
    private val filmsAPI = FilmsAPIService()
    val films = MutableLiveData<List<Film>>()
    val filmsErrorMessage = MutableLiveData<Boolean>()
    val filmsLoading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()

    val popularFilms = MutableLiveData<List<Film>>()
    val popularsLoading = MutableLiveData<Boolean>()

    val latestFilms = MutableLiveData<List<Film>>()
    val latestLoading = MutableLiveData<Boolean>()

    fun getLatestFilms(){
        latestLoading.value = true
        disposable.add(
            filmsAPI.getLatestMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<FilmData>(){
                    override fun onSuccess(t: FilmData) {
                        println("latestfilms onsuccess: ${t.films}")

                        latestFilms.value = t.films
                        latestLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        latestLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    fun getPopularFilms(){
        popularsLoading.value = true
        disposable.add(
            filmsAPI.getPopularMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<FilmData>(){
                    override fun onSuccess(t: FilmData) {
                        popularsLoading.value = false
                        popularFilms.value = t.films
                    }

                    override fun onError(e: Throwable) {
                        popularsLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }
    fun getFilms(){
        filmsLoading.value = true
        disposable.add(
            filmsAPI.getTopRatedFilms()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<FilmData>(){
                    override fun onSuccess(t: FilmData) {
                        films.value = t.films
                        filmsLoading.value = false
                    }
                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        filmsLoading.value = false
                    }

                })
        )


    }


}
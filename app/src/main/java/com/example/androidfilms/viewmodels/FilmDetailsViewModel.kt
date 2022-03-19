package com.example.androidfilms.viewmodels

import FilmsAPIService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidfilms.models.Film
import com.example.androidfilms.models.FilmData
import com.example.androidfilms.models.SimilarFilm
import com.example.androidfilms.models.SimilarFilmsData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class FilmDetailsViewModel : ViewModel() {
    private val filmsAPI = FilmsAPIService()
    val similarFilms = MutableLiveData<List<Film>>()
    val similarFilmsErrorMessage = MutableLiveData<Boolean>()
    val similarFilmsLoading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()

    fun getSimilarMovies(id: Int) {
        similarFilmsLoading.value = true
        disposable.add(
            filmsAPI.getSimilarMovied(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<FilmData>(){
                    override fun onSuccess(t: FilmData) {
                        similarFilms.value = t.films
                        similarFilmsLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        similarFilmsLoading.value = false
                        TODO("Not yet implemented")
                    }

                })
        )

    }

}
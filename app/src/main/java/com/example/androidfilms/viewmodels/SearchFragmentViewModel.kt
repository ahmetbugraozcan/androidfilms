package com.example.androidfilms.viewmodels

import FilmsAPIService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidfilms.models.Film
import com.example.androidfilms.models.FilmData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchFragmentViewModel : ViewModel() {
    private val filmsAPI = FilmsAPIService()
    val searchedFilms = MutableLiveData<List<Film>>()
    val searchLoading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()


    fun searchFilms(searchText: String){
        searchLoading.value = true
        disposable.add(
            filmsAPI.searchMovies(searchText)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<FilmData>(){
                    override fun onSuccess(t: FilmData) {
                        searchedFilms.value = t.films
                        searchLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        searchLoading.value = false
                    }

                })
        )
    }

}
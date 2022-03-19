import com.example.androidfilms.models.Film
import com.example.androidfilms.models.FilmData
import com.example.androidfilms.models.SimilarFilmsData
import com.example.androidfilms.service.FilmsAPI
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FilmsAPIService {
    private val BASE_URL =
        "https://api.themoviedb.org/3/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FilmsAPI::class.java)



    fun getTopRatedFilms(): Single<FilmData > {
        return api.getTopRatedFilms()
    }

    fun getSimilarMovied(id: Int) : Single<FilmData> {
        return api.getSimilarMovies(id)
    }

    fun getPopularMovies() : Single<FilmData>{
        return api.getPopularFilms()
    }

    fun getLatestMovies() : Single<FilmData> {
        return api.getLatestFilms()
    }

    fun searchMovies(searchText: String) : Single<FilmData> {
        return api.searchMovies(searchText)
    }
}
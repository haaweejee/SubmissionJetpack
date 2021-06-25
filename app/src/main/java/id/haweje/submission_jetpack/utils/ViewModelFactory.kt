package id.haweje.submission_jetpack.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.ui.detail.DetailMovieViewModel
import id.haweje.submission_jetpack.ui.detail.DetailTvViewModel
import id.haweje.submission_jetpack.ui.favorite.movie.MovieFavoriteViewModel
import id.haweje.submission_jetpack.ui.favorite.tv.TvFavoriteViewModel
import id.haweje.submission_jetpack.ui.home.movie.MovieViewModel
import id.haweje.submission_jetpack.ui.home.tvshow.TvShowViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(private val movieTvRepository: MovieTvRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this){
                    instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                        instance = this
                    }
                }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieTvRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                TvShowViewModel(movieTvRepository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(movieTvRepository) as T
            }
            modelClass.isAssignableFrom(DetailTvViewModel::class.java) -> {
                DetailTvViewModel(movieTvRepository) as T
            }
            modelClass.isAssignableFrom(MovieFavoriteViewModel::class.java) -> {
                MovieFavoriteViewModel(movieTvRepository) as T
            }
            modelClass.isAssignableFrom(TvFavoriteViewModel::class.java) -> {
                TvFavoriteViewModel(movieTvRepository) as T
            }


            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
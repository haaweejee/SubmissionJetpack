package id.haweje.submission_jetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.vo.Resource

class DetailMovieViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {


    var movieId = MutableLiveData<Int>()

    fun setMovieId(movieId: Int){
        this.movieId.value = movieId
    }

    var getDetailMovie: LiveData<Resource<Movie>> = Transformations.switchMap(movieId){
        movieTvRepository.getMovieDetails(it)
    }

    fun setFavorite(){
        val favoriteResource = getDetailMovie.value
        if (favoriteResource != null){
            val movieFavorite = favoriteResource.data

            if (movieFavorite != null) {
                val state = !movieFavorite.favorite
                movieTvRepository.setFavoriteMovie(movieFavorite,state)
            }
        }
    }


}
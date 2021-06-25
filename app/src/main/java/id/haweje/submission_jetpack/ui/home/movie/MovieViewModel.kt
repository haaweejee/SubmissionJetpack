package id.haweje.submission_jetpack.ui.home.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.vo.Resource

class MovieViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {
    fun getMovieData(): LiveData<Resource<List<Movie>>> = movieTvRepository.getMovieList()
}
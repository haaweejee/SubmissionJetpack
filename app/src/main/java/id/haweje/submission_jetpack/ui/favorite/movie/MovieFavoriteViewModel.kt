package id.haweje.submission_jetpack.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Movie

class MovieFavoriteViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {
    fun getFavoriteMovie(): LiveData<PagedList<Movie>> = movieTvRepository.getFavoriteMovie()
}
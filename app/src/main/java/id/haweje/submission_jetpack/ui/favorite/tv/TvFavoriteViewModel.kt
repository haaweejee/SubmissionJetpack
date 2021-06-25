package id.haweje.submission_jetpack.ui.favorite.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Tv

class TvFavoriteViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {
    fun getFavoriteTv() : LiveData<PagedList<Tv>> = movieTvRepository.getFavoriteTv()
}
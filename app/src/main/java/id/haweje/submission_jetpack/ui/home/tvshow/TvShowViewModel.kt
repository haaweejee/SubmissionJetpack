package id.haweje.submission_jetpack.ui.home.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.vo.Resource

class TvShowViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    fun getTvshowData() : LiveData<Resource<List<Tv>>> = movieTvRepository.getTvShowList()

}
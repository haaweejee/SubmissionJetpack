package id.haweje.submission_jetpack.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.vo.Resource

class DetailTvViewModel(private val movieTvRepository: MovieTvRepository) : ViewModel() {

    var tvId = MutableLiveData<Int>()

    fun setTvId(tvId: Int){
        this.tvId.value = tvId
    }

    var getDetailTv : LiveData<Resource<Tv>> = Transformations.switchMap(tvId){
        movieTvRepository.getTvDetails(it)
    }

    fun setFavorite(){
        val favoriteResource = getDetailTv.value
        if (favoriteResource != null){
            val tvFavorite = favoriteResource.data

            if (tvFavorite != null){
                val state = !tvFavorite.favorite
                movieTvRepository.setFavoriteTv(tvFavorite, state)
            }
        }
    }


}
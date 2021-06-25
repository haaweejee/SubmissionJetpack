package id.haweje.submission_jetpack.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.vo.Resource

interface MovieTvDataSource {

    fun getMovieList() : LiveData<Resource<List<Movie>>>

    fun getMovieDetails(movie_Id: Int): LiveData<Resource<Movie>>

    fun setFavoriteMovie(movie: Movie, favorite:Boolean)

    fun insertMovie(movie : List<Movie>)

    fun getFavoriteMovie() : LiveData<PagedList<Movie>>

    fun getTvShowList() : LiveData<Resource<List<Tv>>>

    fun getTvDetails(tv_id: Int) : LiveData<Resource<Tv>>

    fun setFavoriteTv(tv: Tv, favorite: Boolean)

    fun insertTv(tv: List<Tv>)

    fun getFavoriteTv() : LiveData<PagedList<Tv>>

}
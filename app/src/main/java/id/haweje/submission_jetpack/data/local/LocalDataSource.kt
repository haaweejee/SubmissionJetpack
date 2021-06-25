package id.haweje.submission_jetpack.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.data.local.room.MovieTvDao

class LocalDataSource private constructor(private val movieTvDao: MovieTvDao){

    companion object{
        private var INSTANCE : LocalDataSource? = null

        fun getInstance(movieTvDao: MovieTvDao) : LocalDataSource =
            INSTANCE ?: LocalDataSource(movieTvDao)
    }

    fun getMovieList() : LiveData<List<Movie>> = movieTvDao.getMovieList()

    fun getFavoriteMovie() : DataSource.Factory<Int, Movie> = movieTvDao.getFavoriteMovie()

    fun getDetailMovie(movieId: Int) : LiveData<Movie> = movieTvDao.getDetailMovie(movieId)

    fun insertMovie(movie: List<Movie>)  = movieTvDao.insertMovies(movie)

    fun setFavoriteMovie(movie: Movie, favorite : Boolean){
        movie.favorite = favorite
        movieTvDao.updateMovies(movie)
    }

    fun getTvList() : LiveData<List<Tv>> = movieTvDao.getTvList()

    fun getFavoriteTv() : DataSource.Factory<Int,Tv> = movieTvDao.getFavoriteTv()

    fun getDetailTv(tvId: Int) : LiveData<Tv> = movieTvDao.getDetailTv(tvId)

    fun insertTv(tv: List<Tv>) = movieTvDao.insertTv(tv)

    fun setFavoriteTv(tv: Tv, favorite: Boolean){
        tv.favorite = favorite
        movieTvDao.updateTv(tv)
    }
}
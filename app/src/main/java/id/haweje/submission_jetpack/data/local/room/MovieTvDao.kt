package id.haweje.submission_jetpack.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.data.local.entity.Tv

@Dao
interface MovieTvDao {
    //Movie
    @Query("SELECT * FROM movie")
    fun getMovieList() : LiveData<List<Movie>>

    @Query("SELECT * FROM movie where favorite = 1")
    fun getFavoriteMovie(): DataSource.Factory<Int, Movie>

    @Transaction
    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getDetailMovie(movieId : Int) : LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Update
    fun updateMovies(movie: Movie)

    //TV
    @Query("SELECT * FROM tv")
    fun getTvList(): LiveData<List<Tv>>

    @Query("SELECT * FROM tv where favorite = 1")
    fun getFavoriteTv(): DataSource.Factory<Int, Tv>

    @Transaction
    @Query("SELECT * FROM tv WHERE id = :tvId")
    fun getDetailTv(tvId: Int) : LiveData<Tv>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTv(tvs: List<Tv>)

    @Update
    fun updateTv(tv: Tv)
}
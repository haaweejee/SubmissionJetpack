package id.haweje.submission_jetpack.api


import id.haweje.submission_jetpack.BuildConfig
import id.haweje.submission_jetpack.data.remote.movie.MovieModel
import id.haweje.submission_jetpack.data.remote.movie.MovieResponse
import id.haweje.submission_jetpack.data.remote.tv.TvShowModel
import id.haweje.submission_jetpack.data.remote.tv.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("discover/movie")
    fun getMovieData(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_TOKEN,
        @Query("language") language: String= BuildConfig.ENGLISH_US,
        @Query("page") page: Int = 1
    ): Call<MovieResponse>


    @GET("discover/tv")
    fun getTvShowData(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_TOKEN,
        @Query("language") language: String= BuildConfig.ENGLISH_US,
        @Query("page") page: Int = 1
    ): Call<TvShowResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id")movie_id: Int,
        @Query("api_key")apiKey: String = BuildConfig.TMDB_TOKEN,
    ): Call<MovieModel>

    @GET("tv/{tv_id}")
    fun getTvshowDetail(
        @Path("tv_id")tv_id: Int,
        @Query("api_key")apiKey: String = BuildConfig.TMDB_TOKEN
    ): Call<TvShowModel>
}
package id.haweje.submission_jetpack.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.haweje.submission_jetpack.data.local.LocalDataSource
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.data.remote.RemoteDataSource
import id.haweje.submission_jetpack.data.remote.movie.MovieModel
import id.haweje.submission_jetpack.data.remote.tv.TvShowModel
import id.haweje.submission_jetpack.utils.AppExecutors
import id.haweje.submission_jetpack.vo.Resource

class MovieTvRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors)
    : MovieTvDataSource{

    companion object{
        private var instance : MovieTvRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors): MovieTvRepository =
            instance ?: synchronized(this){
                instance ?: MovieTvRepository(
                    remoteDataSource,
                    localDataSource,
                    appExecutors
                ).apply { instance = this }
            }
    }



    override fun getMovieList(): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieModel>>(appExecutors){
            override fun loadFromDb(): LiveData<List<Movie>>  =
                localDataSource.getMovieList()


            override fun shouldFetch(result: List<Movie>?): Boolean =
                result == null || result.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieModel>>> =
                remoteDataSource.getMovieList()

            override fun saveCallResult(response: List<MovieModel>?) {
                val movieList = ArrayList<Movie>()
                if (response != null) {
                    for (movieResponse in response){
                        val movie = Movie(
                            id = movieResponse.id,
                            poster_path = movieResponse.poster_path,
                            original_title = movieResponse.original_title,
                            release_date = movieResponse.release_date,
                            overview = movieResponse.overview,
                            backdrop_path = movieResponse.backdrop_path,
                            favorite = false)
                        movieList.add(movie)
                    }

                    localDataSource.insertMovie(movieList)
                }
            }
        }.asLiveData()
    }

    override fun getMovieDetails(movie_Id: Int): LiveData<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, MovieModel>(appExecutors){
            override fun loadFromDb(): LiveData<Movie> =
                localDataSource.getDetailMovie(movie_Id)


            override fun shouldFetch(result: Movie?): Boolean =
                result == null

            override fun createCall(): LiveData<ApiResponse<MovieModel>> =
                remoteDataSource.getMovieDetails(movie_Id)

            override fun saveCallResult(response: MovieModel?) {

            }
        }.asLiveData()
    }

    override fun setFavoriteMovie(movie: Movie, favorite: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, favorite) }

    override fun insertMovie(movie: List<Movie>) {
        val runnable = {
            if (localDataSource.getMovieList().value.isNullOrEmpty()){
                localDataSource.insertMovie(movie)
            }
        }
        appExecutors.diskIO().execute(runnable)
    }

    override fun getFavoriteMovie(): LiveData<PagedList<Movie>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()
    }

    override fun getTvShowList(): LiveData<Resource<List<Tv>>> {
        return object : NetworkBoundResource<List<Tv>, List<TvShowModel>>(appExecutors){
            override fun loadFromDb(): LiveData<List<Tv>> =
                localDataSource.getTvList()

            override fun shouldFetch(result: List<Tv>?): Boolean =
                result == null || result.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowModel>>> =
                remoteDataSource.getTvShowList()

            override fun saveCallResult(response: List<TvShowModel>?) {
                val tvList = ArrayList<Tv>()
                if (response != null) {
                    for (tvResponse in response) {
                        val tv = Tv(
                            id = tvResponse.id,
                            original_name = tvResponse.original_name,
                            first_air_date = tvResponse.first_air_date,
                            backdrop_path = tvResponse.backdrop_path,
                            overview = tvResponse.overview,
                            poster_path = tvResponse.poster_path,
                            favorite = false
                        )
                        tvList.add(tv)
                    }

                    localDataSource.insertTv(tvList)
                }
            }
        }.asLiveData()
    }

    override fun getTvDetails(tv_id: Int): LiveData<Resource<Tv>> {
        return object : NetworkBoundResource<Tv, TvShowModel>(appExecutors){
            override fun loadFromDb(): LiveData<Tv> =
                localDataSource.getDetailTv(tv_id)


            override fun shouldFetch(result: Tv?): Boolean =
                result == null


            override fun createCall(): LiveData<ApiResponse<TvShowModel>> =
                remoteDataSource.getTvDetails(tv_id)


            override fun saveCallResult(response: TvShowModel?) {

            }
        }.asLiveData()
    }

    override fun setFavoriteTv(tv: Tv, favorite: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteTv(tv, favorite) }
    }

    override fun insertTv(tv: List<Tv>) {
        val runnable = {
            if (localDataSource.getTvList().value.isNullOrEmpty()){
                localDataSource.insertTv(tv)
            }
        }
        appExecutors.diskIO().execute(runnable)
    }

    override fun getFavoriteTv(): LiveData<PagedList<Tv>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(20)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTv(), config).build()
    }
}
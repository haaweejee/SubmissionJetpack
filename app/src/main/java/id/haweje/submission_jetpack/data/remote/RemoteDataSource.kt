package id.haweje.submission_jetpack.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.haweje.submission_jetpack.api.RetrofitClient
import id.haweje.submission_jetpack.data.ApiResponse
import id.haweje.submission_jetpack.data.remote.movie.MovieModel
import id.haweje.submission_jetpack.data.remote.movie.MovieResponse
import id.haweje.submission_jetpack.data.remote.tv.TvShowModel
import id.haweje.submission_jetpack.data.remote.tv.TvShowResponse
import id.haweje.submission_jetpack.utils.Constanta
import id.haweje.submission_jetpack.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource{

    companion object{

        @Volatile
        private var instance : RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
                instance ?: synchronized(this){
                    instance ?: RemoteDataSource()
                }
    }

    fun getMovieList() : LiveData<ApiResponse<List<MovieModel>>>{
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieModel>>>()
        RetrofitClient.apiInstance
                .getMovieData()
                .enqueue(object : Callback<MovieResponse>{
                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                        resultMovie.value = ApiResponse.success(response.body()?.results)
                        Log.d(Constanta.SUCCESS, response.code().toString())
                        EspressoIdlingResource.decrement()
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d(Constanta.FAIL, t.message.toString())
                        EspressoIdlingResource.decrement()
                    }
                })
        return resultMovie
    }

    fun getMovieDetails(movie_Id: Int) : LiveData<ApiResponse<MovieModel>>{
        EspressoIdlingResource.increment()
        val detailMovie = MutableLiveData<ApiResponse<MovieModel>>()
        RetrofitClient.apiInstance
                .getDetailMovie(movie_Id)
                .enqueue(object : Callback<MovieModel>{
                    override fun onResponse(call: Call<MovieModel>, response: Response<MovieModel>) {
                        detailMovie.value = ApiResponse.success(response.body())
                        Log.d(Constanta.SUCCESS, response.code().toString())
                        EspressoIdlingResource.decrement()
                    }

                    override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                        Log.d(Constanta.FAIL, t.message.toString())
                        EspressoIdlingResource.decrement()
                    }
                })
        return detailMovie
    }

    fun getTvShowList() : LiveData<ApiResponse<List<TvShowModel>>>{
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<TvShowModel>>>()
        RetrofitClient.apiInstance
                .getTvShowData()
                .enqueue(object : Callback<TvShowResponse>{
                    override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                        resultTvShow.value = ApiResponse.success(response.body()?.results)
                        Log.d(Constanta.SUCCESS, response.code().toString())
                        EspressoIdlingResource.decrement()
                    }

                    override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                        Log.d(Constanta.FAIL, t.message.toString())
                        EspressoIdlingResource.decrement()
                    }
                })
        return resultTvShow
    }

    fun getTvDetails(tv_id: Int) : LiveData<ApiResponse<TvShowModel>>{
        EspressoIdlingResource.increment()
        val detailTv = MutableLiveData<ApiResponse<TvShowModel>>()
        RetrofitClient.apiInstance
                .getTvshowDetail(tv_id)
                .enqueue(object : Callback<TvShowModel>{
                    override fun onResponse(call: Call<TvShowModel>, response: Response<TvShowModel>) {
                        detailTv.value = ApiResponse.success(response.body())
                        Log.d(Constanta.SUCCESS, response.code().toString())
                        EspressoIdlingResource.decrement()
                    }


                    override fun onFailure(call: Call<TvShowModel>, t: Throwable) {
                        Log.d(Constanta.FAIL, t.message.toString())
                        EspressoIdlingResource.decrement()
                    }
                })
        return detailTv
    }

}
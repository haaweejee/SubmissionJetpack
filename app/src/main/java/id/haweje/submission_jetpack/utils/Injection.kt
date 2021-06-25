package id.haweje.submission_jetpack.utils

import android.content.Context
import id.haweje.submission_jetpack.data.MovieTvRepository
import id.haweje.submission_jetpack.data.local.LocalDataSource
import id.haweje.submission_jetpack.data.local.room.MovieTvDatabase
import id.haweje.submission_jetpack.data.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context) : MovieTvRepository{

        val database = MovieTvDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieTvDao())
        val appExecutors = AppExecutors()

        return MovieTvRepository.getInstance(
            remoteDataSource,
            localDataSource,
            appExecutors)
    }
}
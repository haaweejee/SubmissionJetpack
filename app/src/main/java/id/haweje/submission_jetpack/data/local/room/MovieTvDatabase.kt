package id.haweje.submission_jetpack.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.data.local.entity.Tv

@Database(entities = [Movie::class, Tv::class], version = 1, exportSchema = false)
abstract class MovieTvDatabase : RoomDatabase() {
    abstract fun movieTvDao() : MovieTvDao

    companion object{
        @Volatile
        private var INSTANCE : MovieTvDatabase? = null

        fun getInstance(context: Context) : MovieTvDatabase =
            INSTANCE ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieTvDatabase::class.java,
                    "MovieTvDb"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}
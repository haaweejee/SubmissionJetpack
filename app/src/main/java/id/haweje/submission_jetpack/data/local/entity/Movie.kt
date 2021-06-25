package id.haweje.submission_jetpack.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    var id: Int,
    var poster_path : String,
    var original_title: String,
    var release_date: String,
    var overview: String,
    var backdrop_path: String,
    var favorite: Boolean = false
)
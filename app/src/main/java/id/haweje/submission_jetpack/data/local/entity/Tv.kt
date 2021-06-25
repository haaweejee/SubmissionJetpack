package id.haweje.submission_jetpack.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv")
data class Tv(
    @PrimaryKey
    var id: Int,
    var poster_path: String,
    var original_name: String,
    var first_air_date: String,
    var overview: String,
    var backdrop_path: String,
    var favorite : Boolean = false
)

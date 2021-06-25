package id.haweje.submission_jetpack.data.remote.movie

data class MovieModel(
        var id: Int,
        var poster_path : String,
        var original_title: String,
        var release_date: String,
        var overview: String,
        var backdrop_path: String,
)

package id.haweje.submission_jetpack.utils

import id.haweje.submission_jetpack.data.local.entity.Movie

interface OnItemClickMovieCallback {
    fun onItemClickedMovie(movie: Movie)
}
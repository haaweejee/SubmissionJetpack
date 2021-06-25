package id.haweje.submission_jetpack.ui.home.movie

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import id.haweje.submission_jetpack.R
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.databinding.ItemCardLayoutBinding
import id.haweje.submission_jetpack.utils.OnItemClickMovieCallback

class ListMovieAdapter: RecyclerView.Adapter<ListMovieAdapter.ViewHolder>() {
    private val listMovies = ArrayList<Movie>()
    private var onItemClickMovieCallback : OnItemClickMovieCallback? = null

    fun setOnItemClick(onItemClickMovieCallback: OnItemClickMovieCallback){
        this.onItemClickMovieCallback = onItemClickMovieCallback
    }

    fun setMovies(movies: List<Movie>){
        this.listMovies.clear()
        this.listMovies.addAll(movies)
        notifyDataSetChanged()
        Log.d("", listMovies.toString())
    }

    inner class ViewHolder(private val binding: ItemCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindMovie(movie: Movie){
            with(binding){
                tvItemName.text = movie.original_title
                tvItemDate.text = movie.release_date
                tvItemDesc.text = movie.overview
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original" + movie.poster_path)
                    .transform(RoundedCorners(10))
                    .placeholder(R.drawable.loading)
                    .into(imageItemId)
            }
            binding.root.setOnClickListener {
                onItemClickMovieCallback?.onItemClickedMovie(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemListBinding = ItemCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bindMovie(movie)
    }

    override fun getItemCount(): Int = listMovies.size
}
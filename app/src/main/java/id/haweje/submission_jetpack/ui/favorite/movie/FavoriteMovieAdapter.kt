package id.haweje.submission_jetpack.ui.favorite.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import id.haweje.submission_jetpack.R
import id.haweje.submission_jetpack.data.local.entity.Movie
import id.haweje.submission_jetpack.databinding.ItemCardLayoutBinding
import id.haweje.submission_jetpack.utils.Constanta
import id.haweje.submission_jetpack.utils.OnItemClickMovieCallback

class FavoriteMovieAdapter: PagedListAdapter<Movie, FavoriteMovieAdapter.ViewHolder>(DIFF_CALLBACK){
    
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }
   

    private var onItemClickMovieCallback : OnItemClickMovieCallback? = null

    fun setOnItemClick(onItemClickMovieCallback: OnItemClickMovieCallback){
        this.onItemClickMovieCallback = onItemClickMovieCallback
    }


    inner class ViewHolder(private val binding: ItemCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindMovie(movie: Movie){
            with(binding){
                tvItemName.text = movie.original_title
                tvItemDate.text = movie.release_date
                tvItemDesc.text = movie.overview
                Glide.with(itemView.context)
                    .load(Constanta.POSTER_PATH + movie.poster_path)
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
        val movie = getItem(position)
        if (movie != null) {
            holder.bindMovie(movie)
        }
    }

}
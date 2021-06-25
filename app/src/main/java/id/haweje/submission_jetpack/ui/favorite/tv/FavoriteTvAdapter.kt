package id.haweje.submission_jetpack.ui.favorite.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import id.haweje.submission_jetpack.R
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.databinding.ItemCardLayoutBinding
import id.haweje.submission_jetpack.utils.Constanta
import id.haweje.submission_jetpack.utils.OnItemClickTvshowCallback

class FavoriteTvAdapter: PagedListAdapter<Tv, FavoriteTvAdapter.ViewHolder>(DIFF_CALLBACK){
    
    companion object{
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Tv>(){
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem == newItem
            }

        }
    }
   

    private var onItemClickTvshowCallback : OnItemClickTvshowCallback? = null

    fun setOnItemClick(onItemClickTvshowCallback: OnItemClickTvshowCallback){
        this.onItemClickTvshowCallback = onItemClickTvshowCallback
    }


    inner class ViewHolder(private val binding: ItemCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindMovie(tv: Tv){
            with(binding){
                tvItemName.text = tv.original_name
                tvItemDate.text = tv.first_air_date
                tvItemDesc.text = tv.overview
                Glide.with(itemView.context)
                    .load(Constanta.POSTER_PATH + tv.poster_path)
                    .transform(RoundedCorners(10))
                    .placeholder(R.drawable.loading)
                    .into(imageItemId)
            }
            binding.root.setOnClickListener {
                onItemClickTvshowCallback?.onItemOnClicked(tv) }
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
package id.haweje.submission_jetpack.ui.home.tvshow

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import id.haweje.submission_jetpack.R
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.databinding.ItemCardLayoutBinding
import id.haweje.submission_jetpack.utils.OnItemClickTvshowCallback

class ListTvAdapter : RecyclerView.Adapter<ListTvAdapter.ViewHolder>() {
    private var onItemClick : OnItemClickTvshowCallback? = null
    private var listTvshow = ArrayList<Tv>()

    fun setOnItemClick(onItemClickTvshowCallback: OnItemClickTvshowCallback){
        this.onItemClick = onItemClickTvshowCallback
    }

    fun setTvShow(tvShow: List<Tv>){
        this.listTvshow.clear()
        this.listTvshow.addAll(tvShow)
        notifyDataSetChanged()
        Log.d("", listTvshow.toString())
    }

    inner class ViewHolder(private val binding: ItemCardLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindtvShow(tv: Tv){
            with(binding){
                tvItemName.text = tv.original_name
                tvItemDate.text = tv.first_air_date
                tvItemDesc.text = tv.overview
                Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/original" + tv.poster_path)
                        .transform(RoundedCorners(10))
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(imageItemId)
            }
            binding.root.setOnClickListener { onItemClick?.onItemOnClicked(tv) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTvAdapter.ViewHolder {
        val itemListBinding = ItemCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: ListTvAdapter.ViewHolder, position: Int) {
        val tvShow = listTvshow[position]
        holder.bindtvShow(tvShow)
    }

    override fun getItemCount(): Int = listTvshow.size
}
package id.haweje.submission_jetpack.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import id.haweje.submission_jetpack.R
import id.haweje.submission_jetpack.data.local.entity.Tv
import id.haweje.submission_jetpack.databinding.ActivityDetailTvShowBinding
import id.haweje.submission_jetpack.utils.Constanta
import id.haweje.submission_jetpack.utils.ViewModelFactory
import id.haweje.submission_jetpack.vo.Resource
import id.haweje.submission_jetpack.vo.Status

class DetailTvShowActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "id"
    }

    private lateinit var binding: ActivityDetailTvShowBinding
    private lateinit var viewModel: DetailTvViewModel
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTvShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, factory )[DetailTvViewModel::class.java]
        setDetailTv()
        viewModel.getDetailTv.observe(this, {
            if (it != null){
                when(it.status){
                    Status.LOADING -> showLoading(true)
                    Status.SUCCESS -> {
                        showLoading(false)
                        loadTvDetail(it)
                    }
                    Status.ERROR ->{
                        showLoading(false)
                        Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun loadTvDetail(tv : Resource<Tv>?){
        if (tv != null){
            binding.itemDetailName.text = tv.data?.original_name
            supportActionBar?.title = tv.data?.original_name
            binding.itemDetailDate.text = tv.data?.first_air_date
            binding.itemDetailOverview.text = tv.data?.overview

            //Poster_path
            Glide.with(this)
                .load(Constanta.POSTER_PATH + tv.data?.poster_path)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .transform(RoundedCorners(10))
                .into(binding.itemDetailImage)

            //Poster_path
            Glide.with(this)
                .load(Constanta.BACKDROP_PATH + tv.data?.backdrop_path)
                .transform(RoundedCorners(10))
                .fitCenter()
                .placeholder(R.drawable.loading)
                .into(binding.backdropImage)
        }
    }

    private fun setDetailTv(){
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTvViewModel::class.java]
        viewModel.setTvId(id)
        showLoading(true)
    }

    private fun showLoading(state: Boolean) {
        if (state)
            binding.frameLoading.visibility = View.VISIBLE
        else
            binding.frameLoading.visibility = View.GONE
    }

    override fun onSupportNavigateUp():Boolean{
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        viewModel.getDetailTv.observe(this, {
            if (it != null) {
                when(it.status){
                    Status.LOADING -> showLoading(true)
                    Status.SUCCESS -> if (it.data != null){
                        val state = it.data.favorite
                        setFavoriteState(state)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.btnFav ->{
                viewModel.setFavorite()
                return true
            }
            R.id.btnShare ->{
                val mimeType = "text/plain"
                val tvShow = viewModel.getDetailTv.value?.data?.original_name
                val url = "https://www.themoviedb.org"
                ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Bagikan Series ini Sekarang")
                    .setText("$tvShow Lihat Selengkapnya di $url")
                    .startChooser()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavoriteState(state: Boolean){
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.btnFav)
        if (state){
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorited)
        }else{
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        }
    }


}

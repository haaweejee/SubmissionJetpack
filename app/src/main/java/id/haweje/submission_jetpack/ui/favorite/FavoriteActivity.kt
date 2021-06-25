package id.haweje.submission_jetpack.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.haweje.submission_jetpack.R
import id.haweje.submission_jetpack.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionPagerAdapter = FavoritePagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionPagerAdapter

        binding.tabs.setupWithViewPager(binding.viewPager)
        setupTabIcon()


        supportActionBar?.title = "Favorite"

    }

    private fun setupTabIcon(){
        binding.tabs.getTabAt(0)?.setIcon(R.drawable.ic_video)
        binding.tabs.getTabAt(1)?.setIcon(R.drawable.ic_tv_show)
    }
}
package id.haweje.submission_jetpack.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.haweje.submission_jetpack.R
import id.haweje.submission_jetpack.databinding.ActivityMainBinding
import id.haweje.submission_jetpack.ui.favorite.FavoriteActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionPagerAdapter = HomePagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionPagerAdapter

        binding.tabs.setupWithViewPager(binding.viewPager)
        setupTabIcon()


        supportActionBar?.title = "Home"


        binding.btnFavorite.setOnClickListener {
            goToFavorite()
        }
    }

    private fun goToFavorite(){
        intent = Intent(this, FavoriteActivity::class.java)
        startActivity(intent)
    }

    private fun setupTabIcon(){
        binding.tabs.getTabAt(0)?.setIcon(R.drawable.ic_video)
        binding.tabs.getTabAt(1)?.setIcon(R.drawable.ic_tv_show)
    }
}
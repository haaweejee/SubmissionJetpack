package id.haweje.submission_jetpack.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.haweje.submission_jetpack.R
import id.haweje.submission_jetpack.ui.home.MainActivity
import java.util.*
import kotlin.concurrent.schedule

class SplashScreenActivity : AppCompatActivity() {
    companion object{
        const val DELAY = 1000L
    }

    private var timer = Timer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        timer.schedule(DELAY){
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onPause() {
        timer.cancel()
        super.onPause()
    }

}
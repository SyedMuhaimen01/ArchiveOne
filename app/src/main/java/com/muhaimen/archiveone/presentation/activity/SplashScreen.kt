package com.muhaimen.archiveone.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.muhaimen.archiveone.R

class SplashScreen : AppCompatActivity() {
    private var isSplashVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen: SplashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { isSplashVisible }

        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            isSplashVisible = false
            val intent = Intent(this, HomeActivity::class.java)
            val options = android.app.ActivityOptions.makeCustomAnimation(
                this,
                R.anim.fade_in,
                R.anim.fade_out
            )
            startActivity(intent,options.toBundle())
            finish()
        }, 1500)
    }
}
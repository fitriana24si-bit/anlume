package com.example.ana_anlume.Home.pertemuan_6

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ana_anlume.BaseActivity
import com.example.ana_anlume.R
import com.example.ana_anlume.Home.pertemuan_3.LoginActivity
import com.example.ana_anlume.onboarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefLogin = getSharedPreferences("LOGIN", MODE_PRIVATE)
        val prefOnboarding = getSharedPreferences("ONBOARDING", MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            val isFinishedOnboarding = prefOnboarding.getBoolean("FINISHED", false)
            val isLogin = prefLogin.getBoolean("isLogin", false)

            when {
                !isFinishedOnboarding -> {
                    startActivity(Intent(this, OnBoardingActivity::class.java))
                }
                isLogin -> {
                    startActivity(Intent(this, BaseActivity::class.java))
                }
                else -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            finish()
        }, 2000)
    }
}

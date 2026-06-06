package com.example.ana_anlume.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.ana_anlume.Home.pertemuan_3.LoginActivity
import com.example.ana_anlume.databinding.ActivityOnBoardingBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = OnBoardingAdapter(this)
        binding.viewPager.adapter = adapter

        // Menghubungkan TabLayout dengan ViewPager2 (Dots Indicator)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Tombol muncul hanya di halaman terakhir
                if (position == 2) {
                    binding.btnStart.visibility = View.VISIBLE
                } else {
                    binding.btnStart.visibility = View.GONE
                }
            }
        })

        binding.btnStart.setOnClickListener {
            val pref = getSharedPreferences("ONBOARDING", MODE_PRIVATE)
            pref.edit().putBoolean("FINISHED", true).apply()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}

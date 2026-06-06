package com.example.ana_anlume.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ana_anlume.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = OnBoardingAdapter(this)
        binding.viewPager.adapter = adapter
        
        // Catatan: Dots Indicator dan Tombol "Ayo Mulai" sekarang diatur 
        // langsung di dalam layout Fragment masing-masing agar lebih presisi.
    }
}

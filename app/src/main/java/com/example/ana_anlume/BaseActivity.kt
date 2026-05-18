package com.example.ana_anlume

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ana_anlume.About.AboutFragment
import com.example.ana_anlume.Home.HomeFragment
import com.example.ana_anlume.Profile.ProfileFragment
import com.example.ana_anlume.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ PERBAIKAN: Hanya panggil fragment default jika savedInstanceState null
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.about -> {
                    replaceFragment(AboutFragment())
                    true
                }
                R.id.profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}
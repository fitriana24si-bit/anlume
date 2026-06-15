package com.example.ana_anlume

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ana_anlume.About.AboutFragment
import com.example.ana_anlume.Home.HomeFragment
import com.example.ana_anlume.Home.agenda.AgendaDesaActivity // Impor Activity yang kita buat tadi
import com.example.ana_anlume.Home.dokumen.DokumenPublikFragment
import com.example.ana_anlume.Profile.ProfileFragment
import com.example.ana_anlume.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set fragment default saat pertama kali aplikasi dibuka
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.produk -> {
                    // Karena AgendaDesa adalah Activity, kita buka menggunakan Intent
                    val intent = Intent(this, AgendaDesaActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.dokumen -> {
                    replaceFragment(DokumenPublikFragment())
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
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
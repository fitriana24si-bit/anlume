package com.example.ana_anlume

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ana_anlume.About.AboutFragment
import com.example.ana_anlume.Home.HomeFragment
import com.example.ana_anlume.Home.agenda.AgendaDesaFragment // Impor Fragment-nya, bukan Activity-nya
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
                    // FIX: Sekarang panggil Fragment agar Bottom Nav tidak hilang!
                    replaceFragment(AgendaDesaFragment())
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
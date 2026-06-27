package com.example.ana_anlume

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ana_anlume.About.AboutFragment
import com.example.ana_anlume.Home.HomeFragment
import com.example.ana_anlume.Home.agenda.AgendaDesaFragment
import com.example.ana_anlume.Home.agenda.notification.PermissionHelper
import com.example.ana_anlume.Home.dokumen.DokumenPublikFragment
import com.example.ana_anlume.Profile.ProfileFragment
import com.example.ana_anlume.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    // Launcher untuk izin notifikasi
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "Notifikasi diizinkan", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notifikasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Minta izin notifikasi untuk Android 13+
        if (PermissionHelper.isNotificationPermissionRequired()) {
            if (!PermissionHelper.hasPermission(this, Manifest.permission.POST_NOTIFICATIONS)) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // Deteksi apakah dibuka dari notifikasi
        val openAgenda = intent.getBooleanExtra("open_agenda", false)

        if (savedInstanceState == null) {
            if (openAgenda) {
                replaceFragment(AgendaDesaFragment())
            } else {
                replaceFragment(HomeFragment())
            }
        }

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.produk -> {
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
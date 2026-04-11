package com.example.ana_anlume.pertemuan_3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ana_anlume.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("USERNAME")
        binding.tvWelcome.text = "Selamat Datang, $username"

        binding.btnLanjut.setOnClickListener {
            val intent = Intent(
                this,
                com.example.ana_anlume.pertemuan_4.DashboardActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }
}
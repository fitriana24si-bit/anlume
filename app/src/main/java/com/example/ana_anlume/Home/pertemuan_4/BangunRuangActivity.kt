package com.example.ana_anlume.Home.pertemuan_4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ana_anlume.Home.pertemuan_2.CalcuActivity
import com.example.ana_anlume.R

class BangunRuangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_bangun_ruang)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvJudul = findViewById<TextView>(R.id.tvJudul)
        val tvDeskripsi = findViewById<TextView>(R.id.tvDeskripsi)
        val btnHitung = findViewById<Button>(R.id.btnHitung)
        val btnBack = findViewById<Button>(R.id.btnBack)

        tvJudul.text = intent.getStringExtra("judul")
        tvDeskripsi.text = intent.getStringExtra("deskripsi")

        // 🔥 FIX UTAMA DI SINI
        btnHitung.setOnClickListener {
            val intent = Intent(
                this,
                CalcuActivity::class.java
            )
            startActivity(intent)
        }
            btnBack.setOnClickListener {
                finish()
        }
    }
}
package com.example.ana_anlume.pertemuan_4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ana_anlume.R
import com.example.ana_anlume.pertemuan_3.LoginActivity
import com.example.ana_anlume.pertemuan_6.WebViewActivity
import com.google.android.material.snackbar.Snackbar

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // =========================
        // 🔥 TAMBAHAN PERTEMUAN 6
        // =========================
        val btnWeb = findViewById<Button>(R.id.btnWeb)
        // =========================

        val judul = "Halaman Utama"
        val deskripsi = "Ini adalah menu utama aplikasi"

        // Tombol 1 → Bangun Ruang
        btn1.setOnClickListener {
            val intent = Intent(this, BangunRuangActivity::class.java)
            intent.putExtra("judul", judul)
            intent.putExtra("deskripsi", deskripsi)
            startActivity(intent)
        }

        // Tombol 2 → Custom 1
        btn2.setOnClickListener {
            val intent = Intent(this, Custom1Activity::class.java)
            intent.putExtra("judul", judul)
            intent.putExtra("deskripsi", deskripsi)
            startActivity(intent)
        }

        // Tombol 3 → Custom 2
        btn3.setOnClickListener {
            val intent = Intent(this, Custom2Activity::class.java)
            intent.putExtra("judul", judul)
            intent.putExtra("deskripsi", deskripsi)
            startActivity(intent)
        }

        // =========================
        // 🌐 WEBVIEW BUTTON
        // =========================
        btnWeb.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }

        // =========================
        // 🔐 LOGOUT (DIPERBAIKI)
        // =========================
        btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Konfirmasi Logout")
            builder.setMessage("Apakah kamu yakin ingin logout?")

            builder.setPositiveButton("Ya") { _, _ ->

                // ❗ HAPUS SESSION LOGIN
                val pref = getSharedPreferences("LOGIN", MODE_PRIVATE)
                val editor = pref.edit()
                editor.clear()
                editor.apply()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            builder.setNegativeButton("Tidak") { _, _ ->
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Logout dibatalkan",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            builder.show()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
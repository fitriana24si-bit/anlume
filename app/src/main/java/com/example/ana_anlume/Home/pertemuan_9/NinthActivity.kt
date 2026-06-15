package com.example.ana_anlume.Home.pertemuan_9

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ana_anlume.Home.pertemuan_4.DashboardActivity // Pastikan import ini ada
import com.example.ana_anlume.R

class NinthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ninth)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // ==========================================
        // BUTTON MASUK KE HALAMAN UTAMA (DASHBOARD)
        // ==========================================
        val btnMasukDokumen = findViewById<Button>(R.id.btnMasukDokumen)

        btnMasukDokumen.setOnClickListener {
            // DIUBAH: Mengarah ke DashboardActivity, bukan DokumenActivity lagi
            val intent = Intent(this, DashboardActivity::class.java).apply {
                // Mengirim tanda (flag/extra) untuk memberi tahu Dashboard agar langsung membuka tab Dokumen
                putExtra("BUKA_TAB_DOKUMEN", true)
            }
            startActivity(intent)
            finish() // Menutup NinthActivity agar tidak menumpuk backstack
        }
    }
}
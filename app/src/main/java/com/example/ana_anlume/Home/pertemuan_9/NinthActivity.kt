package com.example.ana_anlume.Home.pertemuan_9

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ana_anlume.Home.dokumen.DokumenActivity
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

        // =========================
        // BUTTON MASUK KE DOKUMEN
        // =========================

        val btnMasukDokumen = findViewById<Button>(R.id.btnMasukDokumen)

        btnMasukDokumen.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    DokumenActivity::class.java
                )
            )
        }
    }
}
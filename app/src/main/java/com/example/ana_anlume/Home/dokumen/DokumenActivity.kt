package com.example.ana_anlume.Home.dokumen

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ana_anlume.R
import com.google.android.material.chip.Chip

class DokumenActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var dokumenList: ArrayList<DokumenModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dokumen)

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
        // COMPONENT
        // =========================

        listView = findViewById(R.id.listDokumen)

        val chipSemua = findViewById<Chip>(R.id.chipSemua)
        val chipPemerintah = findViewById<Chip>(R.id.chipPemerintah)
        val chipLayanan = findViewById<Chip>(R.id.chipLayanan)

        // =========================
        // DATA LISTVIEW
        // =========================

        dokumenList = arrayListOf(

            DokumenModel(
                "Surat Keterangan Domisili",
                "Dokumen administrasi kependudukan desa"
            ),

            DokumenModel(
                "Laporan Dana Desa",
                "Informasi transparansi dana desa"
            ),

            DokumenModel(
                "Panduan Layanan Desa",
                "Panduan penggunaan layanan masyarakat"
            ),

            DokumenModel(
                "Dokumen Bantuan Sosial",
                "Informasi penerima bantuan desa"
            )
        )

        // =========================
        // ADAPTER
        // =========================

        val adapter = DokumenAdapter(this, dokumenList)
        listView.adapter = adapter

        // =========================
        // CLICK LISTVIEW
        // =========================

        listView.setOnItemClickListener { _, _, position, _ ->

            Toast.makeText(
                this,
                "Membuka ${dokumenList[position].judul}",
                Toast.LENGTH_SHORT
            ).show()
        }

        // =========================
        // CHIP FILTER
        // =========================

        chipSemua.setOnClickListener {

            Toast.makeText(
                this,
                "Menampilkan semua dokumen",
                Toast.LENGTH_SHORT
            ).show()
        }

        chipPemerintah.setOnClickListener {

            Toast.makeText(
                this,
                "Kategori Pemerintah",
                Toast.LENGTH_SHORT
            ).show()
        }

        chipLayanan.setOnClickListener {

            Toast.makeText(
                this,
                "Kategori Layanan",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
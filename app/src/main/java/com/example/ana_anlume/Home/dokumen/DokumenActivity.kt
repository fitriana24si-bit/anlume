package com.example.ana_anlume.Home.dokumen

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.ana_anlume.R

class DokumenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dokumen)

        // Inisialisasi ListView dengan data dummy agar tidak kosong
        val listDokumen = findViewById<ListView>(R.id.listDokumen)
        val data = arrayOf(
            "Surat Keterangan Domisili",
            "Kartu Keluarga Baru",
            "Akta Kelahiran",
            "Surat Pengantar Nikah",
            "Laporan Realisasi Anggaran Desa",
            "Pedoman Pengelolaan Dana Desa",
            "Regulasi Kebersihan Lingkungan"
        )
        
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        listDokumen.adapter = adapter
    }
}

package com.example.ana_anlume.pertemuan_2

import com.example.ana_anlume.R
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etAlas = findViewById<EditText>(R.id.etAlas)
        val etTinggi = findViewById<EditText>(R.id.etTinggi)
        val etJari = findViewById<EditText>(R.id.etJari)
        val btnSegitiga = findViewById<Button>(R.id.btnSegitiga)
        val btnBola = findViewById<Button>(R.id.btnBola)
        val tvHasil = findViewById<TextView>(R.id.tvHasil)

        // 🔺 HITUNG SEGITIGA
        btnSegitiga.setOnClickListener {
            val alas = etAlas.text.toString().toDoubleOrNull()
            val tinggi = etTinggi.text.toString().toDoubleOrNull()

            if (alas != null && tinggi != null) {
                val hasil = 0.5 * alas * tinggi
                tvHasil.text = "Luas Segitiga = $hasil"

                Log.d("SEGITIGA", hasil.toString())
            } else {
                Toast.makeText(this, "Input tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        // ⚪ HITUNG BOLA
        btnBola.setOnClickListener {
            val r = etJari.text.toString().toDoubleOrNull()

            if (r != null) {
                val hasil = (4.0 / 3.0) * Math.PI * r.pow(3)
                tvHasil.text = "Volume Bola = $hasil"

                Log.d("BOLA", hasil.toString())
            } else {
                Toast.makeText(this, "Input jari-jari kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
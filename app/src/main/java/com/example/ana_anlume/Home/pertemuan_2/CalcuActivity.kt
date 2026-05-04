package com.example.ana_anlume.Home.pertemuan_2

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.ana_anlume.R

class CalcuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcu)

        val spPilihan = findViewById<Spinner>(R.id.spPilihan)
        val etInput1 = findViewById<EditText>(R.id.etInput1)
        val etInput2 = findViewById<EditText>(R.id.etInput2)
        val btnHitung = findViewById<Button>(R.id.btnHitung)
        val tvHasil = findViewById<TextView>(R.id.tvHasil)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val calcu = Calcu()

        // Isi pilihan
        val pilihan = arrayOf("Luas Segitiga", "Volume Bola")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, pilihan)
        spPilihan.adapter = adapter

        // Ganti hint sesuai pilihan
        spPilihan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    etInput1.hint = "Masukkan Alas"
                    etInput2.visibility = View.VISIBLE
                    etInput2.hint = "Masukkan Tinggi"
                } else {
                    etInput1.hint = "Masukkan Jari-jari"
                    etInput2.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Hitung
        btnHitung.setOnClickListener {
            val pilihanIndex = spPilihan.selectedItemPosition

            if (pilihanIndex == 0) {
                val alas = etInput1.text.toString().toDoubleOrNull()
                val tinggi = etInput2.text.toString().toDoubleOrNull()

                if (alas != null && tinggi != null) {
                    val hasil = calcu.luasSegitiga(alas, tinggi)
                    tvHasil.text = "Luas Segitiga: $hasil"
                } else {
                    Toast.makeText(this, "Isi semua input", Toast.LENGTH_SHORT).show()
                }

            } else {
                val r = etInput1.text.toString().toDoubleOrNull()

                if (r != null) {
                    val hasil = calcu.volumeBola(r)
                    tvHasil.text = "Volume Bola: $hasil"
                } else {
                    Toast.makeText(this, "Masukkan jari-jari", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Tombol kembali
        btnBack.setOnClickListener {
            finish()
        }
    }
}
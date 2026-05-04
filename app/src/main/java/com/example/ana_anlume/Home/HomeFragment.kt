package com.example.ana_anlume.Home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.ana_anlume.R
import com.example.ana_anlume.Home.pertemuan_3.LoginActivity
import com.example.ana_anlume.Home.pertemuan_4.BangunRuangActivity
import com.example.ana_anlume.Home.pertemuan_4.Custom1Activity
import com.example.ana_anlume.Home.pertemuan_4.Custom2Activity
import com.example.ana_anlume.Home.pertemuan_6.WebViewActivity
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AlertDialog

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val btn1 = view.findViewById<Button>(R.id.btn1)
        val btn2 = view.findViewById<Button>(R.id.btn2)
        val btn3 = view.findViewById<Button>(R.id.btn3)
        val btnWeb = view.findViewById<Button>(R.id.btnWeb)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        val judul = "Halaman Utama"
        val deskripsi = "Ini adalah menu utama aplikasi"

        // 🔹 Tombol 1
        btn1.setOnClickListener {
            val intent = Intent(requireContext(), BangunRuangActivity::class.java)
            intent.putExtra("judul", judul)
            intent.putExtra("deskripsi", deskripsi)
            startActivity(intent)
        }

        // 🔹 Tombol 2
        btn2.setOnClickListener {
            val intent = Intent(requireContext(), Custom1Activity::class.java)
            intent.putExtra("judul", judul)
            intent.putExtra("deskripsi", deskripsi)
            startActivity(intent)
        }

        // 🔹 Tombol 3
        btn3.setOnClickListener {
            val intent = Intent(requireContext(), Custom2Activity::class.java)
            intent.putExtra("judul", judul)
            intent.putExtra("deskripsi", deskripsi)
            startActivity(intent)
        }

        // 🔹 WebView
        btnWeb.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        // 🔹 Logout
        btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Konfirmasi Logout")
            builder.setMessage("Apakah kamu yakin ingin logout?")

            builder.setPositiveButton("Ya") { _, _ ->

                val pref = requireActivity().getSharedPreferences("LOGIN", 0)
                pref.edit().clear().apply()

                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }

            builder.setNegativeButton("Tidak") { _, _ ->
                Snackbar.make(view, "Logout dibatalkan", Snackbar.LENGTH_SHORT).show()
            }

            builder.show()
        }
    }
}
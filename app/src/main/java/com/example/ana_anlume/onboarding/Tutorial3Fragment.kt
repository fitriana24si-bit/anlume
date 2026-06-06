package com.example.ana_anlume.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.ana_anlume.Home.pertemuan_3.LoginActivity
import com.example.ana_anlume.R

class Tutorial3Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tutorial3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnStart = view.findViewById<Button>(R.id.btnStartInside)
        btnStart.setOnClickListener {
            // Simpan status onboarding sudah selesai
            val pref = requireActivity().getSharedPreferences("ONBOARDING", Context.MODE_PRIVATE)
            pref.edit().putBoolean("FINISHED", true).apply()

            // Alihkan ke halaman Login
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}

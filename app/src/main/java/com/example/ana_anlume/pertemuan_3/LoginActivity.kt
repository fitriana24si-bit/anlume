package com.example.ana_anlume.pertemuan_3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ana_anlume.databinding.ActivityLoginBinding
import com.example.ana_anlume.pertemuan_4.DashboardActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {

                // =========================
                // 🔥 TAMBAHAN PERTEMUAN 6
                // =========================

                val pref = getSharedPreferences("LOGIN", MODE_PRIVATE)
                val editor = pref.edit()
                editor.putBoolean("isLogin", true)
                editor.apply()

                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
                finish()

            } else {
                binding.etUsername.error = "Isi username"
                binding.etPassword.error = "Isi password"
            }
        }
    }
}
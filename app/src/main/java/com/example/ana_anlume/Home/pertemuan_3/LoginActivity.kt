package com.example.ana_anlume.Home.pertemuan_3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ana_anlume.BaseActivity
import com.example.ana_anlume.databinding.ActivityLoginBinding

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
                val prefUser = getSharedPreferences("USER_DATA", MODE_PRIVATE)
                val savedUsername = prefUser.getString("username", "")
                val savedPassword = prefUser.getString("password", "")

                // Logic Login
                val canLogin = (username == password) || (username == savedUsername && password == savedPassword)

                if (canLogin) {
                    // Simpan status login
                    val prefLogin = getSharedPreferences("LOGIN", MODE_PRIVATE)
                    val editor = prefLogin.edit()
                    editor.putBoolean("isLogin", true)
                    editor.apply()

                    // ✅ PERBAIKAN: Diarahkan ke BaseActivity agar Bottom Navigation & Fragment tampil
                    // Sebelumnya diarahkan ke DashboardActivity, itu sebabnya menu bawah tidak muncul.
                    val intent = Intent(this, BaseActivity::class.java)
                    intent.putExtra("USERNAME", username)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_SHORT).show()
                }

            } else {
                if (username.isEmpty()) binding.etUsername.error = "Isi username"
                if (password.isEmpty()) binding.etPassword.error = "Isi password"
            }
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}

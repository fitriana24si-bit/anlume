package com.example.ana_anlume.Home.pertemuan_3

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.ana_anlume.R
import com.example.ana_anlume.databinding.ActivityRegisterBinding
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDatePicker()
        setupDropdownAgama()

        binding.btnRegister.setOnClickListener {
            if (validateInput()) {
                saveToSharedPreference()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun setupDatePicker() {
        binding.etTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    binding.etTanggalLahir.setText(date)
                },
                year, month, day
            )
            datePickerDialog.show()
        }
    }

    private fun setupDropdownAgama() {
        val agamaList = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Buddha", "Khonghucu")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, agamaList)
        binding.actAgama.setAdapter(adapter)
    }

    private fun validateInput(): Boolean {
        var isValid = true

        val nama = binding.etNama.text.toString()
        val tglLahir = binding.etTanggalLahir.text.toString()
        val agama = binding.actAgama.text.toString()
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        // Validate Nama
        if (nama.isEmpty()) {
            binding.tilNama.error = "Nama tidak boleh kosong"
            isValid = false
        } else {
            binding.tilNama.error = null
        }

        // Validate Tanggal Lahir
        if (tglLahir.isEmpty()) {
            binding.tilTanggalLahir.error = "Tanggal lahir tidak boleh kosong"
            isValid = false
        } else {
            binding.tilTanggalLahir.error = null
        }

        // Validate Jenis Kelamin
        if (binding.rgJenisKelamin.checkedRadioButtonId == -1) {
            binding.tvErrorJK.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.tvErrorJK.visibility = View.GONE
        }

        // Validate Agama
        if (agama.isEmpty()) {
            binding.tilAgama.error = "Agama tidak boleh kosong"
            isValid = false
        } else {
            binding.tilAgama.error = null
        }

        // Validate Username
        if (username.isEmpty()) {
            binding.tilUsername.error = "Username tidak boleh kosong"
            isValid = false
        } else {
            binding.tilUsername.error = null
        }

        // Validate Password
        if (password.isEmpty()) {
            binding.tilPassword.error = "Password tidak boleh kosong"
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        // Validate Confirm Password
        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = "Konfirmasi password tidak boleh kosong"
            isValid = false
        } else if (password != confirmPassword) {
            binding.tilConfirmPassword.error = "Password tidak sama"
            isValid = false
        } else {
            binding.tilConfirmPassword.error = null
        }

        return isValid
    }

    private fun saveToSharedPreference() {
        val pref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val editor = pref.edit()

        val jk = if (binding.rbLaki.isChecked) "Laki-laki" else "Perempuan"

        editor.putString("nama", binding.etNama.text.toString())
        editor.putString("tgl_lahir", binding.etTanggalLahir.text.toString())
        editor.putString("jenis_kelamin", jk)
        editor.putString("agama", binding.actAgama.text.toString())
        editor.putString("username", binding.etUsername.text.toString())
        editor.putString("password", binding.etPassword.text.toString())
        editor.apply()
    }
}

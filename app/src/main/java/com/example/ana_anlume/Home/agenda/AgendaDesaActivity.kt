package com.example.ana_anlume.Home.agenda

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ana_anlume.Data.AppDatabase
import com.example.ana_anlume.databinding.ActivityAgendaDesaBinding
import com.example.ana_anlume.databinding.DialogAddAgendaDesaBinding
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class AgendaDesaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgendaDesaBinding
    private lateinit var adapter: AgendaDesaAdapter
    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgendaDesaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Title Toolbar
        supportActionBar?.title = "Agenda Desa"

        setupRecyclerView()
        loadDataAgenda()

        binding.fabAdd.setOnClickListener {
            showAddDialog()
        }
    }

    private fun setupRecyclerView() {
        adapter = AgendaDesaAdapter(
            emptyList(),
            { agenda ->
                lifecycleScope.launch {
                    db.agendaDesaDao().delete(agenda)
                    Toast.makeText(this@AgendaDesaActivity, "Agenda berhasil dihapus", Toast.LENGTH_SHORT).show()
                    loadDataAgenda()
                }
            },
            { agenda ->
                showEditDialog(agenda)
            }
        )

        binding.rvAgendaDesa.apply {
            layoutManager = LinearLayoutManager(this@AgendaDesaActivity)
            adapter = this@AgendaDesaActivity.adapter
        }
    }

    private fun loadDataAgenda() {
        lifecycleScope.launch {
            val listData = db.agendaDesaDao().getAll()
            adapter.updateData(listData)
        }
    }

    // Fungsi pembantu untuk memunculkan kalender DatePicker
    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format tanggal rapi: DD-MM-YYYY
                val formattedDate = String.format(Locale.getDefault(), "%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear)
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showAddDialog() {
        val dialogBinding = DialogAddAgendaDesaBinding.inflate(LayoutInflater.from(this))

        // Trigger kalender saat kolom tanggal diklik
        dialogBinding.etTanggalKegiatan.setOnClickListener {
            showDatePickerDialog(dialogBinding.etTanggalKegiatan)
        }

        // Buat Dialog tanpa menambahkan tombol setPositiveButton / setNegativeButton bawaan
        val dialog = AlertDialog.Builder(this)
            .setTitle("Tambah Agenda")
            .setView(dialogBinding.root)
            .create()

        // Tombol Batal Custom dari XML
        dialogBinding.btnBatal.setOnClickListener {
            dialog.dismiss()
        }

        // Tombol Simpan Custom dari XML
        dialogBinding.btnSimpan.setOnClickListener {
            val nama = dialogBinding.etNamaKegiatan.text.toString().trim()
            val lokasi = dialogBinding.etLokasi.text.toString().trim()
            val tanggal = dialogBinding.etTanggalKegiatan.text.toString().trim()
            val penanggungJawab = dialogBinding.etPenanggungJawab.text.toString().trim()
            val deskripsi = dialogBinding.etDeskripsi.text.toString().trim()

            if (nama.isNotEmpty() && lokasi.isNotEmpty()) {
                val agendaBaru = AgendaDesaEntity(
                    namaKegiatan = nama,
                    lokasi = lokasi,
                    tanggalKegiatan = tanggal,
                    penanggungJawab = penanggungJawab,
                    deskripsi = deskripsi
                )

                lifecycleScope.launch {
                    db.agendaDesaDao().insert(agendaBaru)
                    Toast.makeText(this@AgendaDesaActivity, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                    loadDataAgenda()
                }
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Nama Kegiatan dan Lokasi wajib diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showEditDialog(agenda: AgendaDesaEntity) {
        val dialogBinding = DialogAddAgendaDesaBinding.inflate(layoutInflater)

        dialogBinding.etNamaKegiatan.setText(agenda.namaKegiatan)
        dialogBinding.etLokasi.setText(agenda.lokasi)
        dialogBinding.etTanggalKegiatan.setText(agenda.tanggalKegiatan)
        dialogBinding.etPenanggungJawab.setText(agenda.penanggungJawab)
        dialogBinding.etDeskripsi.setText(agenda.deskripsi)

        // Ubah teks tombol Simpan menjadi Update pada mode edit
        dialogBinding.btnSimpan.text = "Update"

        // Trigger kalender saat kolom tanggal diklik pada mode EDIT
        dialogBinding.etTanggalKegiatan.setOnClickListener {
            showDatePickerDialog(dialogBinding.etTanggalKegiatan)
        }

        // Buat Dialog tanpa menambahkan tombol setPositiveButton / setNegativeButton bawaan
        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Agenda")
            .setView(dialogBinding.root)
            .create()

        // Tombol Batal Custom dari XML
        dialogBinding.btnBatal.setOnClickListener {
            dialog.dismiss()
        }

        // Tombol Update (btnSimpan) Custom dari XML
        dialogBinding.btnSimpan.setOnClickListener {
            val dataUpdate = agenda.copy(
                namaKegiatan = dialogBinding.etNamaKegiatan.text.toString().trim(),
                lokasi = dialogBinding.etLokasi.text.toString().trim(),
                tanggalKegiatan = dialogBinding.etTanggalKegiatan.text.toString().trim(),
                penanggungJawab = dialogBinding.etPenanggungJawab.text.toString().trim(),
                deskripsi = dialogBinding.etDeskripsi.text.toString().trim()
            )

            lifecycleScope.launch {
                db.agendaDesaDao().update(dataUpdate)
                Toast.makeText(this@AgendaDesaActivity, "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
                loadDataAgenda()
            }
            dialog.dismiss()
        }

        dialog.show()
    }
}
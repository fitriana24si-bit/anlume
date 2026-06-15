package com.example.ana_anlume.Home.dokumen

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ana_anlume.Data.AppDatabase
import com.example.ana_anlume.databinding.ActivityDokumenPublikBinding
import com.example.ana_anlume.databinding.DialogAddDokumenPublikBinding
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class DokumenPublikFragment : Fragment() {

    private var _binding: ActivityDokumenPublikBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: DokumenAdapter
    private val db by lazy { AppDatabase.getDatabase(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityDokumenPublikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadDataDokumen()

        binding.fabAdd.setOnClickListener {
            showAddDialog()
        }
    }

    private fun setupRecyclerView() {
        adapter = DokumenAdapter(
            emptyList(),
            { dokumen ->
                lifecycleScope.launch {
                    db.dokumenDao().delete(dokumen)
                    Toast.makeText(requireContext(), "Dokumen berhasil dihapus", Toast.LENGTH_SHORT).show()
                    loadDataDokumen()
                }
            },
            { dokumen ->
                showEditDialog(dokumen)
            }
        )

        binding.rvDokumenPublik.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@DokumenPublikFragment.adapter
        }
    }

    private fun loadDataDokumen() {
        lifecycleScope.launch {
            val listData = db.dokumenDao().getAll()
            adapter.updateData(listData)
        }
    }

    // Fungsi pembantu untuk memunculkan Kalender otomatis
    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format(Locale.getDefault(), "%02d-%02d-%d", selectedDay, selectedMonth + 1, selectedYear)
                editText.setText(formattedDate)
            },
            year, month, day
        ).show()
    }

    private fun showAddDialog() {
        val dialogBinding = DialogAddDokumenPublikBinding.inflate(LayoutInflater.from(requireContext()))

        // Pasang date picker pada kolom tanggal
        dialogBinding.etTanggalUpload.setOnClickListener {
            showDatePickerDialog(dialogBinding.etTanggalUpload)
        }

        // Buat basis dialog tanpa tombol default bawaan sistem
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Tambah Dokumen")
            .setView(dialogBinding.root)
            .create()

        // Handler tombol Batal dari XML
        dialogBinding.btnBatal.setOnClickListener {
            dialog.dismiss()
        }

        // Handler tombol Simpan dari XML
        dialogBinding.btnSimpan.setOnClickListener {
            val nama = dialogBinding.etNamaDokumen.text.toString().trim()
            val jenis = dialogBinding.etJenisDokumen.text.toString().trim()
            val deskripsi = dialogBinding.etDeskripsi.text.toString().trim()
            val tanggal = dialogBinding.etTanggalUpload.text.toString().trim()

            if (nama.isNotEmpty() && jenis.isNotEmpty()) {
                val dokumenBaru = DokumenEntity(
                    namaDokumen = nama,
                    jenisDokumen = jenis,
                    deskripsi = deskripsi,
                    tanggalUpload = tanggal
                )

                lifecycleScope.launch {
                    db.dokumenDao().insert(dokumenBaru)
                    Toast.makeText(requireContext(), "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                    loadDataDokumen()
                }
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Nama dan Jenis Dokumen wajib diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showEditDialog(dokumen: DokumenEntity) {
        val dialogBinding = DialogAddDokumenPublikBinding.inflate(layoutInflater)

        // Set data lama ke form
        dialogBinding.etNamaDokumen.setText(dokumen.namaDokumen)
        dialogBinding.etJenisDokumen.setText(dokumen.jenisDokumen)
        dialogBinding.etDeskripsi.setText(dokumen.deskripsi)
        dialogBinding.etTanggalUpload.setText(dokumen.tanggalUpload)

        // Ubah text tombol Simpan menjadi Update khusus mode edit
        dialogBinding.btnSimpan.text = "Update"

        // Pasang date picker pada kolom tanggal di mode edit
        dialogBinding.etTanggalUpload.setOnClickListener {
            showDatePickerDialog(dialogBinding.etTanggalUpload)
        }

        // Buat basis dialog tanpa tombol default bawaan sistem
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Edit Dokumen")
            .setView(dialogBinding.root)
            .create()

        // Handler tombol Batal dari XML
        dialogBinding.btnBatal.setOnClickListener {
            dialog.dismiss()
        }

        // Handler tombol Update dari XML
        dialogBinding.btnSimpan.setOnClickListener {
            val dataUpdate = dokumen.copy(
                namaDokumen = dialogBinding.etNamaDokumen.text.toString().trim(),
                jenisDokumen = dialogBinding.etJenisDokumen.text.toString().trim(),
                deskripsi = dialogBinding.etDeskripsi.text.toString().trim(),
                tanggalUpload = dialogBinding.etTanggalUpload.text.toString().trim()
            )

            lifecycleScope.launch {
                db.dokumenDao().update(dataUpdate)
                Toast.makeText(requireContext(), "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
                loadDataDokumen()
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
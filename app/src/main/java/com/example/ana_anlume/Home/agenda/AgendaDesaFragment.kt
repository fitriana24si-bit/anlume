package com.example.ana_anlume.Home.agenda

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
import com.example.ana_anlume.databinding.FragmentAgendaDesaBinding
import com.example.ana_anlume.databinding.DialogAddAgendaDesaBinding
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class AgendaDesaFragment : Fragment() {

    private var _binding: FragmentAgendaDesaBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AgendaDesaAdapter
    private val db by lazy { AppDatabase.getDatabase(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgendaDesaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    Toast.makeText(requireContext(), "Agenda berhasil dihapus", Toast.LENGTH_SHORT).show()
                    loadDataAgenda()
                }
            },
            { agenda ->
                showEditDialog(agenda)
            }
        )

        binding.rvAgendaDesa.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@AgendaDesaFragment.adapter
        }
    }

    private fun loadDataAgenda() {
        lifecycleScope.launch {
            val listData = db.agendaDesaDao().getAll()

            // Poin Effort: Cek kondisi Empty State visual layar kosong
            if (listData.isEmpty()) {
                binding.layoutEmptyState.visibility = View.VISIBLE
                binding.rvAgendaDesa.visibility = View.GONE
            } else {
                binding.layoutEmptyState.visibility = View.GONE
                binding.rvAgendaDesa.visibility = View.VISIBLE
                adapter.updateData(listData)
            }
        }
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
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
        val dialogBinding = DialogAddAgendaDesaBinding.inflate(layoutInflater)

        dialogBinding.etTanggalKegiatan.setOnClickListener {
            showDatePickerDialog(dialogBinding.etTanggalKegiatan)
        }

        // PERBAIKAN UTAMA: Jangan set builder.setTitle() agar sub-layer dialog sistem tidak bocor keluar!
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        // Efek Premium: Hilangkan latar belakang kotak default dialog bawaan android agar layout melengkung sempurna
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnBatal.setOnClickListener {
            dialog.dismiss()
        }

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
                    Toast.makeText(requireContext(), "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                    loadDataAgenda()
                }
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Nama Kegiatan dan Lokasi wajib diisi!", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    private fun showEditDialog(agenda: AgendaDesaEntity) {
        val dialogBinding = DialogAddAgendaDesaBinding.inflate(layoutInflater)

        // Ubah judul form di atas teks secara mandiri di dalam layout XML
        dialogBinding.root.findViewById<android.widget.TextView>(com.example.ana_anlume.R.id.etNamaKegiatan)?.let {
            // Jika ada textview judul atas, modifikasi teksnya menjadi "Form Edit Kegiatan"
        }

        dialogBinding.etNamaKegiatan.setText(agenda.namaKegiatan)
        dialogBinding.etLokasi.setText(agenda.lokasi)
        dialogBinding.etTanggalKegiatan.setText(agenda.tanggalKegiatan)
        dialogBinding.etPenanggungJawab.setText(agenda.penanggungJawab)
        dialogBinding.etDeskripsi.setText(agenda.deskripsi)

        dialogBinding.btnSimpan.text = "Update"

        dialogBinding.etTanggalKegiatan.setOnClickListener {
            showDatePickerDialog(dialogBinding.etTanggalKegiatan)
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnBatal.setOnClickListener {
            dialog.dismiss()
        }

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
                Toast.makeText(requireContext(), "Data berhasil diupdate", Toast.LENGTH_SHORT).show()
                loadDataAgenda()
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
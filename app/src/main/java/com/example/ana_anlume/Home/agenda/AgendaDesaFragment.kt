package com.example.ana_anlume.Home.agenda

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ana_anlume.Data.AppDatabase
import com.example.ana_anlume.Home.agenda.notification.ReminderScheduler
import com.example.ana_anlume.databinding.DialogAddAgendaDesaBinding
import com.example.ana_anlume.databinding.FragmentAgendaDesaBinding
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class AgendaDesaFragment : Fragment() {

    private var _binding: FragmentAgendaDesaBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: AgendaDesaAdapter
    private lateinit var db: AppDatabase
    private var reminderMinute = 10

    companion object {
        private const val TAG = "AgendaDesaFragment"
    }

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

        // Inisialisasi database hanya jika fragment sudah terattach
        if (isAdded) {
            db = AppDatabase.getDatabase(requireContext())
        } else {
            Log.e(TAG, "Fragment not attached to context")
            return
        }

        setupRecyclerView()
        loadDataAgenda()

        binding.fabAdd.setOnClickListener {
            if (isAdded) showAddDialog()
        }
    }

    private fun setupRecyclerView() {
        if (!isAdded) return

        adapter = AgendaDesaAdapter(
            emptyList(),
            { agenda ->
                lifecycleScope.launch {
                    try {
                        db.agendaDesaDao().delete(agenda)
                        ReminderScheduler.cancelReminder(requireContext(), agenda.namaKegiatan)
                        Toast.makeText(requireContext(), "Agenda dihapus", Toast.LENGTH_SHORT).show()
                        loadDataAgenda()
                    } catch (e: Exception) {
                        Log.e(TAG, "Delete error", e)
                        Toast.makeText(requireContext(), "Gagal hapus", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            { agenda ->
                if (isAdded) showEditDialog(agenda)
            }
        )

        binding.rvAgendaDesa.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@AgendaDesaFragment.adapter
        }
    }

    private fun loadDataAgenda() {
        if (!isAdded) return

        lifecycleScope.launch {
            try {
                val list = db.agendaDesaDao().getAll()
                if (list.isEmpty()) {
                    binding.layoutEmptyState.visibility = View.VISIBLE
                    binding.rvAgendaDesa.visibility = View.GONE
                } else {
                    binding.layoutEmptyState.visibility = View.GONE
                    binding.rvAgendaDesa.visibility = View.VISIBLE
                    adapter.updateData(list)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Load data error", e)
                Toast.makeText(requireContext(), "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ============= DIALOG TAMBAH =============
    private fun showAddDialog() {
        if (!isAdded) return

        try {
            val dialogBinding = DialogAddAgendaDesaBinding.inflate(layoutInflater)
            setupSpinner(dialogBinding)

            dialogBinding.etTanggalKegiatan.setOnClickListener {
                showDatePickerDialog(dialogBinding.etTanggalKegiatan)
            }
            dialogBinding.etJamKegiatan.setOnClickListener {
                showTimePickerDialog(dialogBinding.etJamKegiatan)
            }

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            dialogBinding.btnBatal.setOnClickListener { dialog.dismiss() }
            dialogBinding.btnSimpan.setOnClickListener {
                try {
                    val nama = dialogBinding.etNamaKegiatan.text.toString().trim()
                    val lokasi = dialogBinding.etLokasi.text.toString().trim()
                    val tanggal = dialogBinding.etTanggalKegiatan.text.toString().trim()
                    val jam = dialogBinding.etJamKegiatan.text.toString().trim()
                    val penanggung = dialogBinding.etPenanggungJawab.text.toString().trim()
                    val deskripsi = dialogBinding.etDeskripsi.text.toString().trim()

                    if (nama.isNotEmpty() && lokasi.isNotEmpty() && tanggal.isNotEmpty() && jam.isNotEmpty()) {
                        val agenda = AgendaDesaEntity(
                            namaKegiatan = nama,
                            lokasi = lokasi,
                            tanggalKegiatan = tanggal,
                            jamKegiatan = jam,
                            penanggungJawab = penanggung,
                            deskripsi = deskripsi
                        )
                        lifecycleScope.launch {
                            db.agendaDesaDao().insert(agenda)
                            val trigger = calculateTriggerTime(tanggal, jam, reminderMinute)
                            ReminderScheduler.scheduleReminder(requireContext(), trigger, nama)
                            Toast.makeText(
                                requireContext(),
                                "Agenda ditambahkan. Pengingat $reminderMinute menit sebelum kegiatan.",
                                Toast.LENGTH_LONG
                            ).show()
                            loadDataAgenda()
                            dialog.dismiss()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Nama, Lokasi, Tanggal, Jam wajib diisi", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Save error", e)
                    Toast.makeText(requireContext(), "Gagal simpan", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.show()
        } catch (e: Exception) {
            Log.e(TAG, "showAddDialog error", e)
            Toast.makeText(requireContext(), "Gagal buka dialog", Toast.LENGTH_SHORT).show()
        }
    }

    // ============= DIALOG EDIT =============
    private fun showEditDialog(agenda: AgendaDesaEntity) {
        if (!isAdded) return

        try {
            val dialogBinding = DialogAddAgendaDesaBinding.inflate(layoutInflater)
            dialogBinding.etNamaKegiatan.setText(agenda.namaKegiatan)
            dialogBinding.etLokasi.setText(agenda.lokasi)
            dialogBinding.etTanggalKegiatan.setText(agenda.tanggalKegiatan)
            dialogBinding.etJamKegiatan.setText(agenda.jamKegiatan)
            dialogBinding.etPenanggungJawab.setText(agenda.penanggungJawab)
            dialogBinding.etDeskripsi.setText(agenda.deskripsi)

            setupSpinner(dialogBinding)

            dialogBinding.etTanggalKegiatan.setOnClickListener {
                showDatePickerDialog(dialogBinding.etTanggalKegiatan)
            }
            dialogBinding.etJamKegiatan.setOnClickListener {
                showTimePickerDialog(dialogBinding.etJamKegiatan)
            }

            dialogBinding.btnSimpan.text = "Update"

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogBinding.root)
                .create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            dialogBinding.btnBatal.setOnClickListener { dialog.dismiss() }
            dialogBinding.btnSimpan.setOnClickListener {
                try {
                    val nama = dialogBinding.etNamaKegiatan.text.toString().trim()
                    val lokasi = dialogBinding.etLokasi.text.toString().trim()
                    val tanggal = dialogBinding.etTanggalKegiatan.text.toString().trim()
                    val jam = dialogBinding.etJamKegiatan.text.toString().trim()
                    val penanggung = dialogBinding.etPenanggungJawab.text.toString().trim()
                    val deskripsi = dialogBinding.etDeskripsi.text.toString().trim()

                    if (nama.isNotEmpty() && lokasi.isNotEmpty() && tanggal.isNotEmpty() && jam.isNotEmpty()) {
                        val updated = agenda.copy(
                            namaKegiatan = nama,
                            lokasi = lokasi,
                            tanggalKegiatan = tanggal,
                            jamKegiatan = jam,
                            penanggungJawab = penanggung,
                            deskripsi = deskripsi
                        )
                        lifecycleScope.launch {
                            db.agendaDesaDao().update(updated)
                            ReminderScheduler.cancelReminder(requireContext(), agenda.namaKegiatan)
                            val trigger = calculateTriggerTime(tanggal, jam, reminderMinute)
                            ReminderScheduler.scheduleReminder(requireContext(), trigger, nama)
                            Toast.makeText(
                                requireContext(),
                                "Agenda diperbarui. Pengingat $reminderMinute menit sebelum kegiatan.",
                                Toast.LENGTH_LONG
                            ).show()
                            loadDataAgenda()
                            dialog.dismiss()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Nama, Lokasi, Tanggal, Jam wajib diisi", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Update error", e)
                    Toast.makeText(requireContext(), "Gagal update", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.show()
        } catch (e: Exception) {
            Log.e(TAG, "showEditDialog error", e)
            Toast.makeText(requireContext(), "Gagal buka dialog edit", Toast.LENGTH_SHORT).show()
        }
    }

    // ============= HELPER SPINNER =============
    private fun setupSpinner(binding: DialogAddAgendaDesaBinding) {
        if (!isAdded) return
        try {
            val items = listOf("5 Menit", "10 Menit", "15 Menit", "30 Menit", "60 Menit")
            binding.spinnerReminder.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                items
            )
            binding.spinnerReminder.setSelection(1) // default 10 menit
            binding.spinnerReminder.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    reminderMinute = when (position) {
                        0 -> 5
                        1 -> 10
                        2 -> 15
                        3 -> 30
                        else -> 60
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        } catch (e: Exception) {
            Log.e(TAG, "setupSpinner error", e)
        }
    }

    // ============= DATE / TIME PICKER =============
    private fun showDatePickerDialog(editText: EditText) {
        if (!isAdded) return
        val cal = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, y, m, d ->
                val formatted = String.format(Locale.getDefault(), "%02d-%02d-%d", d, m + 1, y)
                editText.setText(formatted)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showTimePickerDialog(editText: EditText) {
        if (!isAdded) return
        val cal = Calendar.getInstance()
        TimePickerDialog(
            requireContext(),
            { _, h, min ->
                val formatted = String.format(Locale.getDefault(), "%02d:%02d", h, min)
                editText.setText(formatted)
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    // ============= HITUNG TRIGGER TIME =============
    private fun calculateTriggerTime(tanggal: String, jam: String, reminderMinute: Int): Long {
        try {
            val partsDate = tanggal.split("-")
            val day = partsDate[0].toInt()
            val month = partsDate[1].toInt() - 1
            val year = partsDate[2].toInt()
            val partsTime = jam.split(":")
            val hour = partsTime[0].toInt()
            val minute = partsTime[1].toInt()

            return Calendar.getInstance().apply {
                set(year, month, day, hour, minute, 0)
                set(Calendar.MILLISECOND, 0)
                add(Calendar.MINUTE, -reminderMinute)
            }.timeInMillis
        } catch (e: Exception) {
            Log.e(TAG, "calculateTriggerTime error", e)
            return System.currentTimeMillis() + (reminderMinute * 60 * 1000L)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
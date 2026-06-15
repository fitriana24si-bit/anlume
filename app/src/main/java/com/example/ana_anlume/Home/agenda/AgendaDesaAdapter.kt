package com.example.ana_anlume.Home.agenda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ana_anlume.databinding.ItemAgendaDesaBinding

class AgendaDesaAdapter(
    private var listAgenda: List<AgendaDesaEntity>,
    private val onDeleteClick: (AgendaDesaEntity) -> Unit,
    private val onEditClick: (AgendaDesaEntity) -> Unit
) : RecyclerView.Adapter<AgendaDesaAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAgendaDesaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(agenda: AgendaDesaEntity) {
            binding.tvNamaKegiatan.text = agenda.namaKegiatan
            binding.tvLokasi.text = agenda.lokasi
            binding.tvTanggalKegiatan.text = agenda.tanggalKegiatan
            binding.tvPenanggungJawab.text = agenda.penanggungJawab
            binding.tvDeskripsi.text = agenda.deskripsi

            binding.btnDelete.setOnClickListener { onDeleteClick(agenda) }
            binding.btnEdit.setOnClickListener { onEditClick(agenda) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAgendaDesaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listAgenda[position])
    }

    override fun getItemCount(): Int = listAgenda.size

    fun updateData(newList: List<AgendaDesaEntity>) {
        listAgenda = newList
        notifyDataSetChanged()
    }
}
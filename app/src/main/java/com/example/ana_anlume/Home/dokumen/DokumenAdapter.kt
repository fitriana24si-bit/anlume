package com.example.ana_anlume.Home.dokumen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ana_anlume.databinding.ItemDokumenPublikBinding

class DokumenAdapter(
    private var listDokumen: List<DokumenEntity>,
    private val onDeleteClick: (DokumenEntity) -> Unit,
    private val onEditClick: (DokumenEntity) -> Unit // Ditambahkan callback edit
) : RecyclerView.Adapter<DokumenAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemDokumenPublikBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dokumen: DokumenEntity) {
            binding.tvNamaDokumen.text = dokumen.namaDokumen
            binding.tvJenisDokumen.text = dokumen.jenisDokumen
            binding.tvDeskripsi.text = dokumen.deskripsi
            binding.tvTanggalUpload.text = dokumen.tanggalUpload

            // Aksi tombol Hapus
            binding.btnDelete.setOnClickListener {
                onDeleteClick(dokumen)
            }

            // Aksi tombol Edit
            binding.btnEdit.setOnClickListener {
                onEditClick(dokumen)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemDokumenPublikBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(listDokumen[position])
    }

    override fun getItemCount(): Int {
        return listDokumen.size
    }

    fun updateData(newList: List<DokumenEntity>) {
        listDokumen = newList
        notifyDataSetChanged()
    }
}
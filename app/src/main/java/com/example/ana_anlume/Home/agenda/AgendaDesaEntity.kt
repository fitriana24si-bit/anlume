package com.example.ana_anlume.Home.agenda

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agenda_desa")
data class AgendaDesaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val namaKegiatan: String,
    val lokasi: String,
    val tanggalKegiatan: String,
    val penanggungJawab: String,
    val deskripsi: String
)
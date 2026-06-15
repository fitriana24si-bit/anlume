package com.example.ana_anlume.Home.dokumen

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dokumen")
data class DokumenEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val namaDokumen: String,
    val jenisDokumen: String,
    val deskripsi: String,
    val tanggalUpload: String
)
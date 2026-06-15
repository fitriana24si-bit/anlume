package com.example.ana_anlume.Home.dokumen

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface DokumenDao {

    @Query("SELECT * FROM dokumen ORDER BY id DESC")
    suspend fun getAll(): List<DokumenEntity>

    @Insert
    suspend fun insert(dokumen: DokumenEntity)

    @Update
    suspend fun update(dokumen: DokumenEntity)

    @Delete
    suspend fun delete(dokumen: DokumenEntity)
}
package com.example.ana_anlume.Home.agenda

import androidx.room.*
// TAMBAHKAN IMPORT INI:
import com.example.ana_anlume.Home.agenda.AgendaDesaEntity

@Dao
interface AgendaDesaDao {

    @Query("SELECT * FROM agenda_desa ORDER BY id DESC")
    suspend fun getAll(): List<AgendaDesaEntity>

    @Insert
    suspend fun insert(data: AgendaDesaEntity)

    @Update
    suspend fun update(data: AgendaDesaEntity)

    @Delete
    suspend fun delete(data: AgendaDesaEntity)
}
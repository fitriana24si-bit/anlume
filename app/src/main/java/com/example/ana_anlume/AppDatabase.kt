package com.example.ana_anlume.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ana_anlume.Home.dokumen.DokumenDao
import com.example.ana_anlume.Home.dokumen.DokumenEntity
import com.example.ana_anlume.Home.agenda.AgendaDesaDao
import com.example.ana_anlume.Home.agenda.AgendaDesaEntity

@Database(
    entities = [
        DokumenEntity::class,
        AgendaDesaEntity::class // Menambahkan entitas Agenda Desa
    ],
    version = 2, // Versi dinaikkan menjadi 2 karena ada penambahan tabel baru
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dokumenDao(): DokumenDao
    abstract fun agendaDesaDao(): AgendaDesaDao // Menambahkan abstract function untuk DAO Agenda Desa

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ana_anlume_db"
                )
                    // Ditambahkan agar aplikasi tidak crash saat mendeteksi perubahan versi database
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
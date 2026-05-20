package com.example.ana_anlume.Home.pertemuan_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ana_anlume.R

class ServiceListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvServices = view.findViewById<RecyclerView>(R.id.rvProducts)
        
        // Data Layanan Desa (Bina Desaku) - Tanpa Gambar
        val serviceList = listOf(
            ServiceModel(1, "Pembuatan Kartu Keluarga", "Administrasi", "Layanan pengajuan pembuatan atau pembaruan KK bagi warga baru atau pindahan.", "Aktif - Jam Kerja"),
            ServiceModel(2, "Surat Keterangan Usaha", "Ekonomi", "Bagi warga yang membutuhkan legalitas usaha untuk pengajuan kredit atau izin.", "Selesai dalam 1 Hari"),
            ServiceModel(3, "Pengaduan Masyarakat", "Sosial", "Wadah aspirasi dan keluhan warga terkait fasilitas desa atau keamanan.", "Online 24 Jam"),
            ServiceModel(4, "Izin Keramaian", "Keamanan", "Prosedur peminjaman balai desa atau izin acara hajatan warga.", "Minimal H-7 Acara"),
            ServiceModel(5, "Layanan Ambulans Desa", "Kesehatan", "Fasilitas transportasi darurat gratis untuk warga yang sakit atau melahirkan.", "Siaga 24 Jam"),
            ServiceModel(6, "Legalitas Surat Tanah", "Pertanahan", "Konsultasi dan pengurusan administrasi jual beli atau hibah tanah desa.", "Janji Temu Dulu")
        )

        rvServices.layoutManager = LinearLayoutManager(requireContext())
        rvServices.adapter = ServiceAdapter(serviceList)
    }
}

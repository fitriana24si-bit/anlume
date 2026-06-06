package com.example.ana_anlume.Home.pertemuan_10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ana_anlume.R

class ProductListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvProducts = view.findViewById<RecyclerView>(R.id.rvProducts)
        
        // Data Produk Hukum & Dokumen Desa (Bina Desaku) dengan Gambar URL
        val documentList = listOf(
            ProductModel(1, "Perdes No. 4 Tahun 2023", "Peraturan Desa", "Tentang Pengelolaan Sampah Rumah Tangga di lingkungan desa.", "15 Jan 2023", "https://picsum.photos/seed/shoe1/400/300"),
            ProductModel(2, "SK Kepala Desa No. 12", "Surat Keputusan", "Pembentukan Satuan Tugas Linmas Desa untuk pengamanan wilayah.", "02 Feb 2023", "https://picsum.photos/seed/shoe2/400/300"),
            ProductModel(3, "Perdes No. 1 Tahun 2024", "Peraturan Desa", "Anggaran Pendapatan dan Belanja Desa (APBDes) Tahun Anggaran 2024.", "28 Des 2023", "https://picsum.photos/seed/shoe3/400/300"),
            ProductModel(4, "Surat Edaran Keamanan", "Surat Edaran", "Himbauan pelaksanaan jaga malam (Siskamling) bagi seluruh warga.", "10 Mar 2024", "https://picsum.photos/seed/shoe4/400/300"),
            ProductModel(5, "Perdes No. 2 Tahun 2023", "Peraturan Desa", "Tentang Tata Tertib Penggunaan Balai Pertemuan Desa.", "20 Jun 2023", "https://picsum.photos/seed/shoe5/400/300"),
            ProductModel(6, "SK Kader Posyandu", "Surat Keputusan", "Penetapan pengurus dan kader posyandu Dahlia Desa Anlume.", "05 Jan 2024", "https://picsum.photos/seed/shoe6/400/300")
        )

        // Menggunakan GridLayoutManager dengan 2 kolom
        rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        rvProducts.adapter = ProductAdapter(documentList)
    }
}

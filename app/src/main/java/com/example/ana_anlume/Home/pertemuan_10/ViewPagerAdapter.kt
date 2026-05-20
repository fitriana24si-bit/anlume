package com.example.ana_anlume.Home.pertemuan_10

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2 // 2 Tab: Produk Hukum dan Layanan

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductListFragment() // Tab 1: Produk Hukum (Grid dengan Gambar)
            else -> ServiceListFragment() // Tab 2: Layanan (List Data Saja)
        }
    }
}

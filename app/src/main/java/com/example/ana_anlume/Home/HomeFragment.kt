package com.example.ana_anlume.Home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.ana_anlume.R
import com.example.ana_anlume.Home.pertemuan_3.LoginActivity
import com.example.ana_anlume.Home.pertemuan_4.BangunRuangActivity
import com.example.ana_anlume.Home.pertemuan_4.Custom1Activity
import com.example.ana_anlume.Home.pertemuan_4.Custom2Activity
import com.example.ana_anlume.Home.pertemuan_6.WebViewActivity
import com.example.ana_anlume.Home.pertemuan_9.NinthActivity
import com.example.ana_anlume.Data.Api.PostApiClient
import com.example.ana_anlume.Data.Model.PostModel
import com.example.ana_anlume.Home.berita.PostAdapter
import com.example.ana_anlume.Home.berita.PhotoAdapter
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var rvBerita: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private val listPost = mutableListOf<PostModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- SLIDER FOTO (Menggunakan drawable yang tersedia) ---
        val vpSlider = view.findViewById<ViewPager2>(R.id.vpSlider)
        val listFoto = listOf(
            R.drawable.gb_desa,
            R.drawable.bg_header_dokumen,
            R.drawable.bg_header_wave
        )
        val photoAdapter = PhotoAdapter(listFoto)
        vpSlider.adapter = photoAdapter

        // --- LIST BERITA ---
        rvBerita = view.findViewById(R.id.rvBerita)
        rvBerita.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter(listPost)
        rvBerita.adapter = postAdapter

        fetchBerita()

        // --- MENU UTAMA ---
        val btn1 = view.findViewById<Button>(R.id.btn1)
        val btn2 = view.findViewById<Button>(R.id.btn2)
        val btn3 = view.findViewById<Button>(R.id.btn3)
        val btnWeb = view.findViewById<Button>(R.id.btnWeb)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnDokumen = view.findViewById<Button>(R.id.btnDokumen)

        btn1.setOnClickListener {
            startActivity(Intent(requireContext(), BangunRuangActivity::class.java))
        }

        btn2.setOnClickListener {
            startActivity(Intent(requireContext(), Custom1Activity::class.java))
        }

        btn3.setOnClickListener {
            startActivity(Intent(requireContext(), Custom2Activity::class.java))
        }

        btnWeb.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        btnDokumen.setOnClickListener {
            startActivity(Intent(requireContext(), NinthActivity::class.java))
        }

        btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah kamu yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    val pref = requireActivity().getSharedPreferences("LOGIN", 0)
                    pref.edit().clear().apply()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }

    private fun fetchBerita() {
        PostApiClient.instance.getPosts().enqueue(object : Callback<List<PostModel>> {
            override fun onResponse(call: Call<List<PostModel>>, response: Response<List<PostModel>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    if (posts != null) {
                        listPost.clear()
                        listPost.addAll(posts.take(10))
                        postAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(requireContext(), "Gagal mengambil data berita", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                Toast.makeText(requireContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

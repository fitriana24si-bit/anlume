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
import com.example.ana_anlume.BaseActivity
import com.example.ana_anlume.Home.pertemuan_3.LoginActivity
import com.example.ana_anlume.Home.pertemuan_4.BangunRuangActivity
import com.example.ana_anlume.Home.pertemuan_4.Custom1Activity
import com.example.ana_anlume.Home.pertemuan_4.Custom2Activity
import com.example.ana_anlume.Home.pertemuan_6.WebViewActivity
import com.example.ana_anlume.Home.dokumen.DokumenActivity
import com.example.ana_anlume.Home.pertemuan_10.TenthActivity
import com.example.ana_anlume.Data.Api.PostApiClient
import com.example.ana_anlume.Data.Model.PostModel
import com.example.ana_anlume.Home.berita.PostAdapter
import com.example.ana_anlume.Home.berita.PhotoAdapter
import androidx.appcompat.app.AlertDialog
import com.example.ana_anlume.Home.pertemuan_13.ThirteenthActivity
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

        // --- SLIDER FOTO ---
        val vpSlider = view.findViewById<ViewPager2>(R.id.vpSlider)
        val listFotoUrl = listOf(
            "https://picsum.photos/id/10/800/400",
            "https://picsum.photos/id/13/800/400",
            "https://picsum.photos/id/17/800/400"
        )
        val photoAdapter = PhotoAdapter(listFotoUrl)
        vpSlider.adapter = photoAdapter

        // --- LIST BERITA ---
        rvBerita = view.findViewById(R.id.rvBerita)
        rvBerita.layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter(listPost)
        rvBerita.adapter = postAdapter

        fetchBerita()
        setupActions(view)
    }

    private fun setupActions(view: View) {
        val btn1 = view.findViewById<Button>(R.id.btn1)
        val btn2 = view.findViewById<Button>(R.id.btn2)
        val btn3 = view.findViewById<Button>(R.id.btn3)
        val btnWeb = view.findViewById<Button>(R.id.btnWeb)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val btnDokumen = view.findViewById<Button>(R.id.btnDokumen)
        val btnTenth = view.findViewById<Button>(R.id.btnTenth)
        val btnThirteenth = view.findViewById<Button>(R.id.btnThirteenth)

        btn1?.setOnClickListener {
            startActivity(Intent(requireContext(), BangunRuangActivity::class.java))
        }

        btn2?.setOnClickListener {
            startActivity(Intent(requireContext(), Custom1Activity::class.java))
        }

        btn3?.setOnClickListener {
            startActivity(Intent(requireContext(), Custom2Activity::class.java))
        }

        btnWeb?.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        btnDokumen?.setOnClickListener {
            // Membuka DokumenActivity (activity_dokumen.xml)
            startActivity(Intent(requireContext(), DokumenActivity::class.java))
        }

        btnTenth?.setOnClickListener {
            // Membuka TenthActivity (Pertemuan 10)
            startActivity(Intent(requireContext(), TenthActivity::class.java))
        }
        btnThirteenth.setOnClickListener {
            startActivity(
                Intent(requireContext(), ThirteenthActivity::class.java)
            )
        }

        btnLogout?.setOnClickListener {
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
                    response.body()?.let {
                        listPost.clear()
                        listPost.addAll(it.take(10))
                        postAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<List<PostModel>>, t: Throwable) {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Gagal memuat berita: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

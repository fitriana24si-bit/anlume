package com.example.ana_anlume.Home.berita

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.ana_anlume.Data.Model.PostModel
import com.example.ana_anlume.R

class PostAdapter(private val posts: List<PostModel>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val judulIndo = listOf(
        "Pedoman Baru Pengelolaan Dana Desa 2024",
        "Penyusunan Rencana Kerja Pembangunan Desa",
        "Regulasi Pemanfaatan Lahan Hijau Desa",
        "Tata Cara Pengajuan Bantuan Sosial Desa",
        "Peningkatan Kapasitas Aparatur Desa Digital",
        "Keputusan Kepala Desa tentang Lingkungan",
        "Laporan Realisasi Pembangunan Infrastruktur",
        "Program Digitalisasi Kependudukan Desa",
        "Penerapan Produk Hukum Desa Terpadu",
        "Inovasi Layanan Publik Desa Mandiri"
    )

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvNewsTitle)
        val tvBody: TextView = view.findViewById(R.id.tvNewsBody)
        val ivNews: ImageView = view.findViewById(R.id.ivNews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        
        val displayTitle = if (position < judulIndo.size) judulIndo[position] else "Informasi Desa: ${post.title.take(20)}..."
        val displayBody = "Pemerintah Desa merilis regulasi terkait ${displayTitle.lowercase()}. Hal ini bertujuan untuk meningkatkan transparansi dan kesejahteraan masyarakat."

        holder.tvTitle.text = displayTitle
        holder.tvBody.text = displayBody

        // Gunakan Picsum Photos agar lebih stabil + Transition agar smooth
        Glide.with(holder.itemView.context)
            .load("https://picsum.photos/seed/${post.id}/400/300")
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.desa) // Pastikan drawable ini ada dan bukan kotak ungu
            .error(android.R.drawable.ic_menu_report_image) // Gambar error default android jika gagal
            .into(holder.ivNews)
    }

    override fun getItemCount(): Int = posts.size
}

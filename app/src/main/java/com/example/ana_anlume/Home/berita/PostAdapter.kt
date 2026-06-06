package com.example.ana_anlume.Home.berita

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ana_anlume.Data.Model.PostModel
import com.example.ana_anlume.R

class PostAdapter(private val posts: List<PostModel>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvNewsTitle)
        val tvBody: TextView = view.findViewById(R.id.tvNewsBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.tvTitle.text = "Regulasi: ${post.title}"
        holder.tvBody.text = post.body
    }

    override fun getItemCount(): Int = posts.size
}

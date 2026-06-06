package com.example.ana_anlume.Home.pertemuan_10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ana_anlume.R

class ProductAdapter(private val productList: List<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProduct: ImageView = view.findViewById(R.id.ivProduct)
        val tvProductName: TextView = view.findViewById(R.id.tvProductName)
        val tvProductDesc: TextView = view.findViewById(R.id.tvProductDesc)
        val tvProductDate: TextView = view.findViewById(R.id.tvProductDate)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.tvProductName.text = product.title
        holder.tvProductDesc.text = product.description
        holder.tvProductDate.text = product.date
        holder.tvCategory.text = product.category
        
        // Menggunakan Glide untuk memuat gambar dari URL
        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .placeholder(R.drawable.about) // Gambar sementara saat loading
            .error(R.drawable.about)       // Gambar jika error
            .into(holder.ivProduct)
    }

    override fun getItemCount(): Int = productList.size
}

package com.example.ana_anlume.Home.dokumen

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.ana_anlume.R

class DokumenAdapter(
    private val context: Activity,
    private val listDokumen: ArrayList<DokumenModel>
) : ArrayAdapter<DokumenModel>(context, R.layout.item_dokumen, listDokumen) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = LayoutInflater.from(context)

        val view = convertView ?: inflater.inflate(
            R.layout.item_dokumen,
            parent,
            false
        )

        // COMPONENT
        val imgIcon = view.findViewById<ImageView>(R.id.imgIcon)
        val txtJudul = view.findViewById<TextView>(R.id.txtJudul)
        val txtDeskripsi = view.findViewById<TextView>(R.id.txtDeskripsi)

        // DATA
        val dokumen = listDokumen[position]

        txtJudul.text = dokumen.judul
        txtDeskripsi.text = dokumen.deskripsi

        imgIcon.setImageResource(R.drawable.ic_launcher_foreground)

        return view
    }
}
package com.example.ana_anlume.Home.pertemuan_10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ana_anlume.R

class ServiceAdapter(private val serviceList: List<ServiceModel>) :
    RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    class ServiceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvServiceCategory: TextView = view.findViewById(R.id.tvServiceCategory)
        val tvServiceName: TextView = view.findViewById(R.id.tvServiceName)
        val tvServiceDesc: TextView = view.findViewById(R.id.tvServiceDesc)
        val tvServiceStatus: TextView = view.findViewById(R.id.tvServiceStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = serviceList[position]
        holder.tvServiceName.text = service.name
        holder.tvServiceCategory.text = service.category
        holder.tvServiceDesc.text = service.description
        holder.tvServiceStatus.text = service.status
    }

    override fun getItemCount(): Int = serviceList.size
}

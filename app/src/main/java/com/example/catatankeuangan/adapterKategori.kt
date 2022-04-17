package com.example.catatankeuangan

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class adapterKategori(
    private val listKategori: ArrayList<dc_kategori>
): RecyclerView.Adapter<adapterKategori.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback{
        fun onItemClicked(data : dc_kategori)
        fun delData(data : dc_kategori)

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var _namaKategori : TextView = itemView.findViewById(R.id.tv_IK_kategori)
        var _jenisKategori : TextView = itemView.findViewById(R.id.tv_IK_jenisKategori)

        var _btnDelete : ImageButton = itemView.findViewById(R.id.btn_IK_delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kategori, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var kategori = listKategori[position]

        holder._namaKategori.setText(kategori.namaKategori)
        holder._jenisKategori.setText(kategori.jenisKategori)
        holder._btnDelete.setOnClickListener{
            var kirimdeletednotes=listKategori[position]
            listKategori.removeAt(position)
            notifyDataSetChanged()
            onItemClickCallback.delData(kirimdeletednotes)

        }

    }

    override fun getItemCount(): Int {
        return listKategori.size
    }
}
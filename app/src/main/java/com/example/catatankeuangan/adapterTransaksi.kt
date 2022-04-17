package com.example.catatankeuangan

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class adapterTransaksi(
    private val listTransaksi: ArrayList<dc_transaksi>
): RecyclerView.Adapter<adapterTransaksi.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback{
        fun onItemClicked(data : dc_transaksi)
        fun delData(data : dc_transaksi)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var _tanggalTransaksi : TextView = itemView.findViewById(R.id.tv_IM_tanggal)
        var _kategoriTransaksi : TextView = itemView.findViewById(R.id.tv_IM_kategori)
        var _keteranganTransaksi : TextView = itemView.findViewById(R.id.tv_IM_keterangan)
        var _jumlahTransaksi : TextView = itemView.findViewById(R.id.tv_IM_uang)
        var _btnDelete : ImageButton = itemView.findViewById(R.id.btn_IM_delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main_transaksi, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var transaksi = listTransaksi[position]

        holder._tanggalTransaksi.setText(transaksi.tanggalTransaksi)
        holder._kategoriTransaksi.setText(transaksi.kategoriTransaksi)
        holder._keteranganTransaksi.setText(transaksi.keteranganTransaksi)
        holder._jumlahTransaksi.setText(transaksi.jumlahTransaksi)

        holder._btnDelete.setOnClickListener{
            //var kirimdeletetransaksi = listTransaksi[position]
            listTransaksi.removeAt(position)
            notifyDataSetChanged()
            onItemClickCallback.delData(transaksi)
        }
    }

    override fun getItemCount(): Int {
        return listTransaksi.size    }
}
package com.example.catatankeuangan

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

var db:FirebaseFirestore = FirebaseFirestore.getInstance()

class MainActivity : AppCompatActivity() {

    lateinit var db : FirebaseFirestore

    //ITEM TRANSAKSI
    //private lateinit var adapter_transaksi : adapterTransaksi
    private var arTransaksi = arrayListOf<dc_transaksi>()
    private lateinit var _rvTransaksi : RecyclerView
    private lateinit var notesadaptertransaksi : adapterTransaksi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()


        //MAINACTIVITY
        val _btn_add_transaksi = findViewById<FloatingActionButton>(R.id.btn_main_add_transaksi)
        val _btn_lihat_laporan = findViewById<Button>(R.id.btn_main_laporan)

        //ITEM TRANSAKSI
        var _tanggalTransaksi = findViewById<TextView>(R.id.tv_IM_tanggal)
        var _kategoriTransaksi = findViewById<TextView>(R.id.tv_IM_kategori)
        var _keteranganTransaksi = findViewById<TextView>(R.id.tv_IM_keterangan)
        var _jumlahTransaksi = findViewById<TextView>(R.id.tv_IM_uang)
        notesadaptertransaksi = adapterTransaksi(arTransaksi)
        _rvTransaksi = findViewById(R.id.rv_main_transaksi)


        _rvTransaksi.layoutManager = LinearLayoutManager(this)
        _rvTransaksi.adapter = notesadaptertransaksi

        notesadaptertransaksi.setOnItemClickCallback(object : adapterTransaksi.OnItemClickCallback{
            override fun onItemClicked(data: dc_transaksi) {
                TODO("Not yet implemented")
            }

            override fun delData(data: dc_transaksi) {
                db.collection("tbTransaksi").document(data.idTransaksi)
                    .delete()
                Toast.makeText(applicationContext,"delete nih",
                    Toast.LENGTH_SHORT).show()
                Log.d("idTransaksi", data.idTransaksi)

            }

        })



        /*if(arTransaksi.size>0){
            notesadaptertransaksi = adapterTransaksi(arTransaksi)
            _rvTransaksi.layoutManager = LinearLayoutManager(this)
            _rvTransaksi.adapter = notesadaptertransaksi
        }*/


        _btn_add_transaksi.setOnClickListener{
            val intent = Intent(this@MainActivity, tambah_transaksi::class.java)
            startActivity(intent)
        }

        _btn_lihat_laporan.setOnClickListener{
            val intent = Intent(this@MainActivity, pop_up_laporan_keuangan::class.java)
            startActivity(intent)
        }

        //GET DATA TRANSAKSI FROM DATABASE
        db.collection("tbTransaksi").get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    val idTransaksi = dc_transaksi(
                        document.data.get("idTransaksi").toString(),
                        document.data.get("tanggalTransaksi").toString(),
                        document.data.get("kategoriTransaksi").toString(),
                        document.data.get("keteranganTransaksi").toString(),
                        document.data.get("jumlahTransaksi").toString()
                    )
                    arTransaksi.add(idTransaksi)
                }
                notesadaptertransaksi.notifyDataSetChanged()


            }

            .addOnFailureListener{
                Log.d("Firebase", it.message.toString())
            }



    }


}
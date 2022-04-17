package com.example.catatankeuangan

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class laporan_keuangan : AppCompatActivity() {
    lateinit var db : FirebaseFirestore

    //ITEM TRANSAKSI
    private lateinit var adapter_transaksi : adapterTransaksi
    private var arTransaksi = arrayListOf<dc_transaksi>()
    private lateinit var _rvTransaksi : RecyclerView
    private lateinit var notesadaptertransaksi : adapterTransaksi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_keuangan)

        db = FirebaseFirestore.getInstance()

        //ITEM TRANSAKSI
        var _tanggalTransaksi = findViewById<TextView>(R.id.tv_IM_tanggal)
        var _kategoriTransaksi = findViewById<TextView>(R.id.tv_IM_kategori)
        var _keteranganTransaksi = findViewById<TextView>(R.id.tv_IM_keterangan)
        var _jumlahTransaksi = findViewById<TextView>(R.id.tv_IM_uang)
        notesadaptertransaksi = adapterTransaksi(arTransaksi)
        _rvTransaksi = findViewById(R.id.rv_LK_transaksi)


        _rvTransaksi.layoutManager = LinearLayoutManager(this)
        _rvTransaksi.adapter = notesadaptertransaksi

        val _btn_back_to_main = findViewById<ImageButton>(R.id.btn_LK_backtoMain)
        val tanggal_intent = intent.getStringExtra("kirimTanggal")

        _btn_back_to_main.setOnClickListener{
            val intent = Intent(this@laporan_keuangan, MainActivity::class.java)
            startActivity(intent)
        }

        //GET DATA TRANSAKSI FROM DATABASE
        db.collection("tbTransaksi").get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    if (document.data.get("tanggalTransaksi").toString()==tanggal_intent){
                        val idTransaksi = dc_transaksi(
                            document.data.get("idTransaksi").toString(),
                            document.data.get("tanggalTransaksi").toString(),
                            document.data.get("kategoriTransaksi").toString(),
                            document.data.get("keteranganTransaksi").toString(),
                            document.data.get("jumlahTransaksi").toString()
                        )
                        arTransaksi.add(idTransaksi)
                    }

                }
                notesadaptertransaksi.notifyDataSetChanged()

            }

            .addOnFailureListener{
                Log.d("Firebase", it.message.toString())
            }


    }
}
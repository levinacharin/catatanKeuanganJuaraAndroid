package com.example.catatankeuangan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class Kategori : AppCompatActivity() {

    lateinit var db : FirebaseFirestore

    //ITEM KATEGORI
    private lateinit var adapter_kategori : adapterKategori
    private var arKategori = arrayListOf<dc_kategori>()
    private lateinit var _rvkategori : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategori)

        db = FirebaseFirestore.getInstance()


        val _btn_back_to_TT = findViewById<ImageButton>(R.id.btn_K_backtoTT)
        val _btn_tambah_kategori = findViewById<ImageButton>(R.id.btn_K_addKategori)

        //EDIT TRANSAKSI
        val dataEdited_Intent = intent.getParcelableExtra<dc_kategori>("editedData")
        val dataUnEdited_Intent = intent.getParcelableExtra<dc_kategori>("uneditedData")
        val position = intent.getIntExtra("position",-99)

        //ITEM KATEGORI
        var _namaKategori = findViewById<TextView>(R.id.tv_IK_kategori)
        var _jenisKategori = findViewById<TextView>(R.id.tv_IK_jenisKategori)
        var notesadapterkategori = adapterKategori(arKategori)
        _rvkategori = findViewById(R.id.rv_K_kategori)

        if(arKategori.size>0){
            notesadapterkategori = adapterKategori(arKategori)
            _rvkategori.layoutManager = LinearLayoutManager(this)
            _rvkategori.adapter = notesadapterkategori
        }

        _btn_back_to_TT.setOnClickListener{
            val intent = Intent(this@Kategori, tambah_transaksi::class.java)
            startActivity(intent)
        }

        _btn_tambah_kategori.setOnClickListener{
            val intent = Intent(this@Kategori, pop_up_tambah_kategori::class.java)
            startActivity(intent)
        }


        //GET DATA FROM DATABASE
        db.collection("tbKategori").get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    val namaKategori = dc_kategori(
                        document.data.get("namaKategori").toString(),
                        document.data.get("jenisKategori").toString()
                    )
                    arKategori.add(namaKategori)
                }
                notesadapterkategori = adapterKategori(arKategori)
                _rvkategori.layoutManager = LinearLayoutManager(this)
                _rvkategori.adapter = notesadapterkategori

                notesadapterkategori.setOnItemClickCallback(object : adapterKategori.OnItemClickCallback{
                    override fun onItemClicked(data: dc_kategori) {
                        TODO("Not yet implemented")
                    }

                    override fun delData(data: dc_kategori) {
                        db.collection("tbKategori").document(data.namaKategori)
                            .delete()
                    }


                })

            }

            .addOnFailureListener{
                Log.d("Firebase", it.message.toString())
            }


    }
}
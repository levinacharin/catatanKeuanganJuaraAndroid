package com.example.catatankeuangan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class pop_up_tambah_kategori : AppCompatActivity() {

    lateinit var db : FirebaseFirestore

    //ITEM KATEGORI
    private lateinit var adapter_kategori : adapterKategori


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up_tambah_kategori)
        db = FirebaseFirestore.getInstance()

        var _et_namaKategori = findViewById<EditText>(R.id.et_PU_TK_NamaKategori)
        var _tv_jenisKategori = findViewById<TextView>(R.id.tv_PU_TK_JenisKategori)
        var _grup_radiobutton = findViewById<RadioGroup>(R.id.rbg_PU_TK_pilihan)


        var _btn_simpan = findViewById<Button>(R.id.btn_PU_TK_simpan)
        val _btn_batal = findViewById<Button>(R.id.btn_PU_TK_batal)



        _btn_batal.setOnClickListener{
            val intent = Intent(this@pop_up_tambah_kategori, Kategori::class.java)
            _et_namaKategori.text.clear()
            _tv_jenisKategori = null
            startActivity(intent)
        }

        _grup_radiobutton.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                _tv_jenisKategori.setText(radio.text.toString())
                Toast.makeText(applicationContext," Jenis Kategori :"+
                        " ${radio.text}",
                    Toast.LENGTH_SHORT).show()
            }
        )

        var id: Int = _grup_radiobutton.checkedRadioButtonId
        if (id!=-1){
            val radio:RadioButton = findViewById(id)
            Toast.makeText(applicationContext,"Jenis Kategori :" +
                    " ${radio.text}",
                Toast.LENGTH_SHORT).show()

        }

        _btn_simpan.setOnClickListener{
            var cek: Int = _grup_radiobutton.checkedRadioButtonId
            if (cek!=-1){
                val data = dc_kategori(_et_namaKategori.text.toString(),_tv_jenisKategori.text.toString())

                db.collection("tbKategori").document(data.namaKategori)
                    .set(data)
                    .addOnSuccessListener {
                        val intent = Intent(this@pop_up_tambah_kategori, Kategori::class.java)
                        startActivity(intent)
                        Log.d("Firebase", "berhasil")
                    }
                    .addOnFailureListener{
                        Log.d("Firebase", it.message.toString())
                    }

            }
            else{
                // If no radio button checked in this radio group
                Toast.makeText(applicationContext,"Jenis Kategori Harus Dipilih",
                    Toast.LENGTH_SHORT).show()
            }


        }


    }
}
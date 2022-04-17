package com.example.catatankeuangan

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class tambah_transaksi : AppCompatActivity(), DatePickerDialog.OnDateSetListener{

    lateinit var db : FirebaseFirestore

    //SPINNER KATEGORI
    private var arKategori = arrayListOf<dc_kategori>()

    //TANGGAL
    private lateinit var _tv_tanggal: TextView
    private lateinit var _btn_settanggal: Button
    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    private fun randomIDGenerator(): String {
        return UUID.randomUUID().toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_transaksi)

        //TANGGAL
        _tv_tanggal = findViewById<TextView>(R.id.tv_TT_tanggaloutput)
        _btn_settanggal = findViewById<Button>(R.id.btn_TT_tanggal)


        db = FirebaseFirestore.getInstance()

        //TAMBAH TRANSAKSI
        val _btn_back_to_main = findViewById<ImageButton>(R.id.btn_TT_backtoMain)
        val _btn_simpan = findViewById<Button>(R.id.btn_TT_simpan)
        val _btn_edit_kategori = findViewById<ImageButton>(R.id.btn_TT_editKategori)
        var _et_jumlah = findViewById<EditText>(R.id.et_TT_jumlah)
        var _et_keterangan = findViewById<EditText>(R.id.et_TT_keterangan)
        var _tv_tanggal = findViewById<TextView>(R.id.tv_TT_tanggaloutput)
        var _kategori = findViewById<Spinner>(R.id.spinner_TT_kategori)


        //SPINNER KATEGORI
        var _namaKategori = findViewById<TextView>(R.id.tv_IK_kategori)
        var _listnamakategori : ArrayList<String> = arrayListOf("Kategori:")
        var _spinnerKategori = findViewById<Spinner>(R.id.spinner_TT_kategori)


        //GET DATA KATEGORI FROM DATABASE FOR SPINNER
        db.collection("tbKategori").get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    val namaKategori = dc_kategori(
                        document.data.get("namaKategori").toString(),
                        document.data.get("jenisKategori").toString()
                    )
                    arKategori.add(namaKategori)
                    _listnamakategori.add(document.data.get("namaKategori").toString())
                }
            }

            .addOnFailureListener{
                Log.d("Firebase", it.message.toString())
            }


        _spinnerKategori.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, _listnamakategori)
        _spinnerKategori.onItemSelectedListener

        _spinnerKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //texts = options.get(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //texts = "Please Select an Option"
            }
        }




        _btn_back_to_main.setOnClickListener{
            val intent = Intent(this@tambah_transaksi, MainActivity::class.java)
            startActivity(intent)
        }

        _btn_simpan.setOnClickListener{
            val intent = Intent(this@tambah_transaksi, MainActivity::class.java)
            startActivity(intent)
        }

        _btn_edit_kategori.setOnClickListener{
            val intent = Intent(this@tambah_transaksi, Kategori::class.java)
            startActivity(intent)
        }

        //TANGGAL
        pickDate()

        _btn_simpan.setOnClickListener{
            val data = dc_transaksi(randomIDGenerator(),_tv_tanggal.text.toString(),_kategori.selectedItem.toString(),_et_keterangan.text.toString(),_et_jumlah.text.toString())

            db.collection("tbTransaksi").document(data.idTransaksi)
                .set(data)
                .addOnSuccessListener {
                    val intent = Intent(this@tambah_transaksi, MainActivity::class.java)
                    startActivity(intent)
                    Log.d("Firebase", "berhasil")
                }
                .addOnFailureListener{
                    Log.d("Firebase", it.message.toString())
                }
        }



    }

    //TANGGAL
    private fun getDateCalendar() {
        val cal : Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    private fun pickDate() {
        _btn_settanggal.setOnClickListener {
            getDateCalendar()

            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateCalendar()
        _tv_tanggal.text = "$savedDay-$savedMonth-$savedYear"
    }

}
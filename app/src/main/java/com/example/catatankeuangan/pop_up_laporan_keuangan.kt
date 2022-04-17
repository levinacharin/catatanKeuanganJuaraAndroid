package com.example.catatankeuangan

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import java.util.*

class pop_up_laporan_keuangan : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    //TANGGAL
    private lateinit var _tv_tanggal: TextView
    private lateinit var _btn_settanggal: Button
    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up_laporan_keuangan)

        //TANGGAL
        _tv_tanggal = findViewById<TextView>(R.id.tv_PU_LK_tanggal)
        _btn_settanggal = findViewById<Button>(R.id.btn_PU_LK_tanggal)

        val _btn_lihat = findViewById<Button>(R.id.btn_PU_LK_lihat)
        val _btn_batal = findViewById<Button>(R.id.btn_PU_LK_batal)


        _btn_batal.setOnClickListener{
            val intent = Intent(this@pop_up_laporan_keuangan, MainActivity::class.java)
            startActivity(intent)


        }

        //TANGGAL
        pickDate()

        _btn_lihat.setOnClickListener{
            val intent = Intent(this@pop_up_laporan_keuangan, laporan_keuangan::class.java)
            intent.putExtra("kirimTanggal", _tv_tanggal.text.toString())
            startActivity(intent)
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
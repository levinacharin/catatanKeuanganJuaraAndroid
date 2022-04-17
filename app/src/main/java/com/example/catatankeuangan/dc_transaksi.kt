package com.example.catatankeuangan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class dc_transaksi(
    var idTransaksi: String,
    var tanggalTransaksi : String,
    var kategoriTransaksi : String,
    var keteranganTransaksi : String,
    var jumlahTransaksi : String
) : Parcelable

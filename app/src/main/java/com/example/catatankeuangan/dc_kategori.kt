package com.example.catatankeuangan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class dc_kategori(
    var namaKategori : String,
    var jenisKategori : String
) : Parcelable

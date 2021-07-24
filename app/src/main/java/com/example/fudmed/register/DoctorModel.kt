package com.example.fudmed.register

import android.icu.text.CaseMap

data class DoctorModel (
    val FullName: String,
    val PhoneNumber: String,
    val Email: String,
    val title : String,
    val Occupation: String,
    val Location: String,
    val Gender : String,
    val password: String,
)
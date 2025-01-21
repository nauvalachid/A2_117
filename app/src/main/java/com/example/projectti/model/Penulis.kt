package com.example.projectti.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Penulis(
    @SerialName("id_penulis")
    val idPenulis: Int,
    @SerialName("nama_penulis")
    val namaPenulis: String,
    val biografi: String,
    val kontak: String
)
package com.example.projectti.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Penerbit(
    @SerialName("id_penerbit")
    val idPenerbit: String,
    @SerialName("nama_penerbit")
    val namaPenerbit: String,
    @SerialName("alamat_penerbit")
    val alamatPenerbit: String,
    @SerialName("telepon_penerbit")
    val teleponPenerbit: String
)
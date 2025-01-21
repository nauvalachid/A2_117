package com.example.projectti.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KategoriDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Penerbit
)


@Serializable
data class KategoriResponse(
    val status: Boolean,
    val message: String,
    val data: List<Penerbit>
)

@Serializable
data class Kategori(
    @SerialName("id_kategori")
    val idKategori: String,
    @SerialName("nama_kategori")
    val namaKategori: String,
    @SerialName("deskripsi_kategori")
    val deskripsiKategori: String
)
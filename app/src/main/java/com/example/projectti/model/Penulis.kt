package com.example.projectti.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PenulisDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Penerbit
)


@Serializable
data class PenulisResponse(
    val status: Boolean,
    val message: String,
    val data: List<Penerbit>
)

@Serializable
data class Penulis(
    @SerialName("id_penulis")
    val idPenulis: String,
    @SerialName("nama_penulis")
    val namaPenulis: String,
    val biografi: String,
    val kontak: String
)
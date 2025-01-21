package com.example.projectti.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PenerbitDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Penerbit
)


@Serializable
data class PenerbitResponse(
    val status: Boolean,
    val message: String,
    val data: List<Penerbit>
)

@Serializable
data class Penerbit(
    val id_penerbit: String,
    val nama_penerbit: String,
    val alamat_penerbit: String,
    val telepon_penerbit: String
)
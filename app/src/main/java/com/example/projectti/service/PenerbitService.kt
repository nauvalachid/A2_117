package com.example.projectti.service

import com.example.projectti.model.Penerbit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PenerbitService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Mengambil semua data penerbit
    @GET("penerbit/readpenerbit.php")
    suspend fun getPenerbit(): List<Penerbit>

    // Mengambil satu data penerbit berdasarkan id_penerbit
    @GET("penerbit/read1penerbit.php")
    suspend fun getPenerbitById(@Query("id_penerbit") idPenerbit: String): Penerbit

    // Menambahkan data penerbit baru
    @POST("penerbit/createpenerbit.php")
    suspend fun insertPenerbit(@Body penerbit: Penerbit): Response<Void>

    // Mengupdate data penerbit berdasarkan id_penerbit
    @PUT("penerbit/editpenerbit.php")
    suspend fun updatePenerbit(
        @Query("id_penerbit") idPenerbit: String,
        @Body penerbit: Penerbit
    ): Response<Void>

    // Menghapus data penerbit berdasarkan id_penerbit
    @DELETE("penerbit/deletepenerbit.php")
    suspend fun deletePenerbit(@Query("id_penerbit") idPenerbit: String): Response<Void>
}
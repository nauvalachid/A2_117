package com.example.projectti.service

import com.example.projectti.model.Penerbit
import com.example.projectti.model.Penulis
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PenulisService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Mengambil semua data penulis
    @GET("penulis/readpenulis.php")
    suspend fun getPenulis(): List<Penulis>

    // Mengambil satu data penulis berdasarkan id_penulis
    @GET("penulis/read1penulis.php")
    suspend fun getPenulisById(@Query("id_penulis") idPenulis: String): Penulis

    // Menambahkan data penulis baru
    @POST("penulis/createpenulis.php")
    suspend fun insertPenulis(@Body penulis: Penulis): Response<Void>

    // Mengupdate data penulis berdasarkan id_penulis
    @PUT("penulis/editpenulis.php")
    suspend fun updatePenulis(
        @Query("id_penulis") idPenulis: String,
        @Body penulis: Penulis
    ): Response<Void>

    // Menghapus data penulis berdasarkan id_penulis
    @DELETE("penulis/deletepenulis.php")
    suspend fun deletePenulis(@Query("id_penulis") idPenulis: String): Response<Void>
}
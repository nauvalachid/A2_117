package com.example.projectti.service

import com.example.projectti.model.Kategori
import com.example.projectti.model.Penerbit
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface KategoriService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Mengambil semua data kategori
    @GET("kategori/readkategori.php")
    suspend fun getKategori(): List<Kategori>

    // Mengambil satu data kategori berdasarkan id_kategori
    @GET("kategori/read1kategori.php")
    suspend fun getKategoriById(@Query("id_kategori") idKategori: Int): Kategori

    // Menambahkan data kategori baru
    @POST("kategori/createkategori.php")
    suspend fun insertKategori(@Body kategori: Kategori): Response<Void>

    // Mengupdate data kategori berdasarkan id_kategori
    @PUT("kategori/editkategori.php")
    suspend fun updateKategori(
        @Query("id_kategori") idKategori: Int,
        @Body kategori: Kategori
    ): Response<Void>

    // Menghapus data kategori berdasarkan id_kategori
    @DELETE("kategori/deletekategori.php")
    suspend fun deleteKategori(@Query("id_kategori") idKategori: Int): Response<Void>
}
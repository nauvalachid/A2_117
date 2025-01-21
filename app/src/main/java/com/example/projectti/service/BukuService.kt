package com.example.projectti.service

import com.example.projectti.model.Buku
import com.example.projectti.model.Kategori
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface BukuService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    // Mengambil semua data buku
    @GET("buku/readbuku.php")
    suspend fun getBuku(): List<Buku>

    // Mengambil satu data buku berdasarkan id_buku
    @GET("buku/read1buku.php")
    suspend fun getBukuById(@Query("id_buku") idBuku: Int): Buku

    // Menambahkan data buku baru
    @POST("buku/createbuku.php")
    suspend fun insertBuku(@Body buku: Buku): Response<Void>

    // Mengupdate data buku berdasarkan id_buku
    @PUT("buku/editbuku.php")
    suspend fun updateBuku(
        @Query("id_buku") idBuku: Int,
        @Body buku: Buku
    ): Response<Void>

    // Menghapus data buku berdasarkan id_buku
    @DELETE("buku/deletebuku.php")
    suspend fun deleteBuku(@Query("id_buku") idBuku: Int): Response<Void>
}
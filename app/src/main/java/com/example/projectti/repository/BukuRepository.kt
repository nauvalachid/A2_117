package com.example.projectti.repository

import com.example.projectti.model.Buku
import com.example.projectti.service.BukuService
import okio.IOException

// Interface Repository
interface BukuRepository {
    suspend fun getBuku(): List<Buku>
    suspend fun insertBuku(buku: Buku)
    suspend fun updateBuku(idBuku: Int, buku: Buku)
    suspend fun deleteBuku(idBuku: Int)
    suspend fun getBukuById(idBuku: Int): Buku
}

// Implementasi Repository
class NetworkBukuRepository(
    private val bukuApiService: BukuService
) : BukuRepository {
    override suspend fun getBuku(): List<Buku> =
        bukuApiService.getBuku()

    override suspend fun insertBuku(buku: Buku) {
        val response = bukuApiService.insertBuku(buku)
        if (!response.isSuccessful) {
            throw IOException("Failed to insert buku. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun updateBuku(idBuku: Int, buku: Buku) {
        val response = bukuApiService.updateBuku(idBuku, buku)
        if (!response.isSuccessful) {
            throw IOException("Failed to update buku. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun deleteBuku(idBuku: Int) {
        try {
            val response = bukuApiService.deleteBuku(idBuku)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete buku. HTTP Status code: ${response.code()}")
            } else {
                println("Success: ${response.message()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getBukuById(idBuku: Int): Buku {
        return bukuApiService.getBukuById(idBuku)
    }
}

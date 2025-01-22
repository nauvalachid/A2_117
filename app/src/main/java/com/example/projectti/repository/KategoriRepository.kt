package com.example.projectti.repository

import com.example.projectti.model.Kategori
import com.example.projectti.service.KategoriService
import okio.IOException

interface KategoriRepository {
    suspend fun getKategori(): List<Kategori>
    suspend fun insertKategori(kategori: Kategori)
    suspend fun updateKategori(idKategori: Int, kategori: Kategori)
    suspend fun deleteKategori(idKategori: Int)
    suspend fun getKategoriById(idKategori: Int): Kategori
}

class NetworkKategoriRepository(
    private val kategoriApiService: KategoriService
) : KategoriRepository {
    override suspend fun getKategori(): List<Kategori> =
        kategoriApiService.getKategori()

    override suspend fun insertKategori(kategori: Kategori) {
        val response = kategoriApiService.insertKategori(kategori)
        if (!response.isSuccessful) {
            throw IOException("Failed to insert kategori. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun updateKategori(idKategori: Int, kategori: Kategori) {
        val response = kategoriApiService.updateKategori(idKategori, kategori)
        if (!response.isSuccessful) {
            throw IOException("Failed to update kategori. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun deleteKategori(idKategori: Int) {
        try {
            val response = kategoriApiService.deleteKategori(idKategori)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete kategori. HTTP Status code: ${response.code()}")
            } else {
                println("Success: ${response.message()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getKategoriById(idKategori: Int): Kategori {
        return kategoriApiService.getKategoriById(idKategori)
    }
}

package com.example.projectti.repository

import android.util.Log
import com.example.projectti.model.Buku
import com.example.projectti.model.Penulis
import com.example.projectti.service.PenulisService
import java.io.IOException

interface PenulisRepository {
    suspend fun getPenulis(): List<Penulis>
    suspend fun insertPenulis(penulis: Penulis)
    suspend fun updatePenulis(idPenulis: Int, penulis: Penulis)
    suspend fun deletePenulis(idPenulis: Int)
    suspend fun getPenulisById(idPenulis: Int):Penulis
}

class NetworkPenulisRepository(
    private val penulisApiService: PenulisService
) : PenulisRepository {
    override suspend fun getPenulis(): List<Penulis> =
        penulisApiService.getPenulis()

    override suspend fun insertPenulis(penulis: Penulis) {
        val response = penulisApiService.insertPenulis(penulis)
        if (!response.isSuccessful) {
            throw IOException("Failed to insert penulis. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun updatePenulis(idPenulis: Int, penulis: Penulis) {
        val response = penulisApiService.updatePenulis(idPenulis, penulis)
        if (!response.isSuccessful) {
            throw IOException("Failed to update penulis. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun deletePenulis(idPenulis: Int) {
        try {
            val response = penulisApiService.deletePenulis(idPenulis)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete penulis. HTTP Status code: ${response.code()}")
            } else {
                println("Success: ${response.message()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPenulisById(idPenulis: Int): Penulis {
        Log.d("getPenulisById", "Starting to fetch Penulis with ID: $idPenulis")
        try {
            val penulis = penulisApiService.getPenulisById(idPenulis)
            Log.d("getPenulisById", "Successfully fetched Penulis with ID: $idPenulis")
            return penulis
        } catch (e: IOException) {
            Log.e("getPenulisById", "Failed to fetch Penulis with ID: $idPenulis. Network error occurred.", e)
            throw IOException("Failed to fetch Penulis with ID: $idPenulis. Network error occurred.", e)
        } catch (e: Exception) {
            Log.e("getPenulisById", "An unexpected error occurred while fetching Penulis with ID: $idPenulis.", e)
            throw e
        } finally {
            Log.d("getPenulisById", "Completed the fetch process for Penulis with ID: $idPenulis")
        }
    }
}

package com.example.projectti.repository

import com.example.projectti.model.Penerbit
import com.example.projectti.service.PenerbitService
import okio.IOException

interface PenerbitRepository {
    suspend fun getPenerbit(): List<Penerbit>
    suspend fun insertPenerbit(penerbit: Penerbit)
    suspend fun updatePenerbit(idPenerbit: Int, penerbit: Penerbit)
    suspend fun deletePenerbit(idPenerbit: Int)
    suspend fun getPenerbitById(idPenerbit: Int): Penerbit
}

class NetworkPenerbitRepository(
    private val penerbitApiService: PenerbitService
) : PenerbitRepository {
    override suspend fun getPenerbit(): List<Penerbit> =
        penerbitApiService.getPenerbit()

    override suspend fun insertPenerbit(penerbit: Penerbit) {
        val response = penerbitApiService.insertPenerbit(penerbit)
        if (!response.isSuccessful) {
            throw IOException("Failed to insert penerbit. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun updatePenerbit(idPenerbit: Int, penerbit: Penerbit) {
        val response = penerbitApiService.updatePenerbit(idPenerbit, penerbit)
        if (!response.isSuccessful) {
            throw IOException("Failed to update penerbit. HTTP Status code: ${response.code()}")
        }
    }

    override suspend fun deletePenerbit(idPenerbit: Int) {
        try {
            val response = penerbitApiService.deletePenerbit(idPenerbit)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete penerbit. HTTP Status code: ${response.code()}")
            } else {
                println("Success: ${response.message()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPenerbitById(idPenerbit: Int): Penerbit {
        return penerbitApiService.getPenerbitById(idPenerbit)
    }
}

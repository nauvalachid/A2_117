package com.example.projectti.DependencitionInjection

import com.example.projectti.repository.BukuRepository
import com.example.projectti.repository.KategoriRepository
import com.example.projectti.repository.NetworkBukuRepository
import com.example.projectti.repository.NetworkKategoriRepository
import com.example.projectti.repository.NetworkPenerbitRepository
import com.example.projectti.repository.NetworkPenulisRepository
import com.example.projectti.repository.PenerbitRepository
import com.example.projectti.repository.PenulisRepository
import com.example.projectti.service.BukuService
import com.example.projectti.service.KategoriService
import com.example.projectti.service.PenerbitService
import com.example.projectti.service.PenulisService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bukuRepository : BukuRepository
    val kategoriRepository : KategoriRepository
    val penerbitRepository : PenerbitRepository
    val penulisRepository : PenulisRepository
}

class BukuContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2/PROJECTTI/" //localhost diganti ip kalo run di hp
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val bukuService: BukuService by lazy {retrofit.create(BukuService::class.java)}
    override val bukuRepository: BukuRepository by lazy { NetworkBukuRepository(bukuService) }

    private val kategoriService: KategoriService by lazy {retrofit.create(KategoriService::class.java)}
    override val kategoriRepository: KategoriRepository by lazy { NetworkKategoriRepository(kategoriService) }

    private val penerbitService: PenerbitService by lazy {retrofit.create(PenerbitService::class.java)}
    override val penerbitRepository: PenerbitRepository by lazy { NetworkPenerbitRepository(penerbitService) }

    private val penulisService: PenulisService by lazy {retrofit.create(PenulisService::class.java)}
    override val penulisRepository: PenulisRepository by lazy { NetworkPenulisRepository(penulisService) }
}
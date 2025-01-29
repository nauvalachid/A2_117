package com.example.projectti.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Buku
import com.example.projectti.model.Kategori
import com.example.projectti.repository.BukuRepository
import com.example.projectti.repository.KategoriRepository
import kotlinx.coroutines.launch

class UpdateBukuViewModel(private val bk: BukuRepository) : ViewModel(){
    var bukuuiState by mutableStateOf(UpdateBukuUiState())
        private set

    fun loadBuku(idBuku: Int){
        viewModelScope.launch {
            try{
                val buku = bk.getBukuById(idBuku)
                bukuuiState = UpdateBukuUiState(updateBukuUiEvent = buku.toUpdateBukuUiEvent())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun updateBukuState(updateBukuUiEvent: UpdateBukuUiEvent){
        bukuuiState = UpdateBukuUiState(updateBukuUiEvent = updateBukuUiEvent)
    }

    suspend fun updateBuku(idBuku: Int) {
        viewModelScope.launch {
            try {
                bk.updateBuku(idBuku,bukuuiState.updateBukuUiEvent.toBuku())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class UpdateBukuUiState(
    val updateBukuUiEvent : UpdateBukuUiEvent = UpdateBukuUiEvent()
)

data class UpdateBukuUiEvent(
    val idBuku: Int = 0,
    val namaBuku: String ="",
    val deskripsiBuku: String ="",
    val tanggalTerbit: String ="",
    val statusBuku: String ="",
    val idKategori: String ="",
    val idPenulis: String ="",
    val idPenerbit: String =""
)

fun UpdateBukuUiEvent.toBuku(): Buku = Buku(
    idBuku = idBuku,
    namaBuku = namaBuku,
    deskripsiBuku = deskripsiBuku,
    tanggalTerbit = tanggalTerbit,
    statusBuku = statusBuku,
    idKategori = idKategori,
    idPenulis = idPenulis,
    idPenerbit = idPenerbit
)

fun Buku.toUpdateBukuUiEvent(): UpdateBukuUiEvent = UpdateBukuUiEvent(
    idBuku = idBuku,
    namaBuku = namaBuku,
    deskripsiBuku = deskripsiBuku,
    tanggalTerbit = tanggalTerbit,
    statusBuku = statusBuku,
    idKategori = idKategori,
    idPenulis = idPenulis,
    idPenerbit = idPenerbit
)
package com.example.projectti.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Buku
import com.example.projectti.repository.BukuRepository
import kotlinx.coroutines.launch

class InsertBukuViewModel(private val bk: BukuRepository): ViewModel(){
    var bukuuiState by mutableStateOf(InsertBukuUiState())
        private set
    fun updateInsertbkState(insertBukuUiEvent:InsertBukuUiEvent){
        bukuuiState = InsertBukuUiState(insertBukuUiEvent = insertBukuUiEvent)
    }

    suspend fun insertBk(){
        viewModelScope.launch {
            try {
                bk.insertBuku(bukuuiState.insertBukuUiEvent.toBk())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertBukuUiState(
    val insertBukuUiEvent: InsertBukuUiEvent = InsertBukuUiEvent()
)

data class InsertBukuUiEvent(
    val idBuku: Int = 0,
    val namaBuku: String ="",
    val deskripsiBuku: String ="",
    val tanggalTerbit: String ="",
    val statusBuku: String ="",
    val idKategori: Int = 0,
    val idPenulis: Int = 0,
    val idPenerbit: Int = 0
)

fun InsertBukuUiEvent.toBk(): Buku = Buku(
    idBuku = idBuku,
    namaBuku = namaBuku,
    deskripsiBuku = deskripsiBuku,
    tanggalTerbit = tanggalTerbit,
    statusBuku = statusBuku,
    idKategori = idKategori,
    idPenulis = idPenulis,
    idPenerbit = idPenerbit
)

fun Buku.toUiStateBk(): InsertBukuUiState = InsertBukuUiState(
    insertBukuUiEvent = toInsertBukuUiEvent()
)

fun Buku.toInsertBukuUiEvent(): InsertBukuUiEvent = InsertBukuUiEvent(
    idBuku = idBuku,
    namaBuku = namaBuku,
    deskripsiBuku = deskripsiBuku,
    tanggalTerbit = tanggalTerbit,
    statusBuku = statusBuku,
    idKategori = idKategori,
    idPenulis = idPenulis,
    idPenerbit = idPenerbit
)
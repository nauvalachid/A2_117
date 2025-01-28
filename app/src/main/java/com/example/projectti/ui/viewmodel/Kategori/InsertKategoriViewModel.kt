package com.example.projectti.ui.viewmodel.Kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Kategori
import com.example.projectti.model.Penerbit
import com.example.projectti.repository.KategoriRepository
import com.example.projectti.repository.PenerbitRepository
import kotlinx.coroutines.launch

class InsertKategoriViewModel(private val ktgr: KategoriRepository): ViewModel(){
    var kategoriuiState by mutableStateOf(InsertKategoriUiState())
        private set
    fun updateInsertktgrState(insertKategoriUiEvent:InsertKategoriUiEvent){
        kategoriuiState = InsertKategoriUiState(insertKategoriUiEvent = insertKategoriUiEvent)
    }

    suspend fun insertKtgr(){
        viewModelScope.launch {
            try {
                ktgr.insertKategori(kategoriuiState.insertKategoriUiEvent.toKtgr())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertKategoriUiState(
    val insertKategoriUiEvent: InsertKategoriUiEvent = InsertKategoriUiEvent()
)

data class InsertKategoriUiEvent(
    val idKategori: Int = 0 ,
    val namaKategori: String ="",
    val deskripsiKategori: String ="",
)

fun InsertKategoriUiEvent.toKtgr(): Kategori = Kategori(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)

fun Kategori.toUiStateKtgr(): InsertKategoriUiState = InsertKategoriUiState(
    insertKategoriUiEvent = toInsertKategoriUiEvent()
)

fun Kategori.toInsertKategoriUiEvent(): InsertKategoriUiEvent = InsertKategoriUiEvent(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)
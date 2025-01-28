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

class UpdateKategoriViewModel(private val ktgr: KategoriRepository) : ViewModel(){
    var kategoriuiState by mutableStateOf(UpdateKategoriUiState())
        private set

    fun loadKategori(idKategori: Int){
        viewModelScope.launch {
            try{
                val kategori = ktgr.getKategoriById(idKategori)
                kategoriuiState = UpdateKategoriUiState(updateKategoriUiEvent = kategori.toUpdateKategoriUiEvent())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun updateKategoriState(updateKategoriUiEvent: UpdateKategoriUiEvent){
        kategoriuiState = UpdateKategoriUiState(updateKategoriUiEvent = updateKategoriUiEvent)
    }

    suspend fun updateKategori(idKategori: Int) {
        viewModelScope.launch {
            try {
                ktgr.updateKategori(idKategori,kategoriuiState.updateKategoriUiEvent.toKategori())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class UpdateKategoriUiState(
    val updateKategoriUiEvent : UpdateKategoriUiEvent = UpdateKategoriUiEvent()
)

data class UpdateKategoriUiEvent(
    val idKategori: Int = 0,
    val namaKategori: String ="",
    val deskripsiKategori: String =""
)

fun UpdateKategoriUiEvent.toKategori(): Kategori = Kategori(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)

fun Kategori.toUpdateKategoriUiEvent(): UpdateKategoriUiEvent = UpdateKategoriUiEvent(
    idKategori = idKategori,
    namaKategori = namaKategori,
    deskripsiKategori = deskripsiKategori
)
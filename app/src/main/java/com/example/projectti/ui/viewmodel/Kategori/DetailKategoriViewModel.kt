package com.example.projectti.ui.viewmodel.Kategori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Kategori
import com.example.projectti.model.Penerbit
import com.example.projectti.repository.KategoriRepository
import com.example.projectti.repository.PenerbitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailKategoriUiState{
    object Loading:DetailKategoriUiState()
    data class Success(val kategori: Kategori):DetailKategoriUiState()
    data class Error(val message: String) : DetailKategoriUiState()
}

class DetailKategoriViewModel(private val ktgr: KategoriRepository): ViewModel() {
    private val _kategorituiState = MutableStateFlow<DetailKategoriUiState>(DetailKategoriUiState.Loading)
    val katageoriuiState: StateFlow<DetailKategoriUiState> = _kategorituiState.asStateFlow()

    fun getKategoriById(idKategori: Int) {
        viewModelScope.launch {
            try {
                val kategori = ktgr.getKategoriById(idKategori)
                _kategorituiState.value = DetailKategoriUiState.Success(kategori)
            } catch (e: Exception) {
                _kategorituiState.value = DetailKategoriUiState.Error(e.localizedMessage ?: "Terjadi Kesalahan.")
            }
        }
    }

    fun deleteKtgr(idKategori: Int) {
        viewModelScope.launch {
            try {
                ktgr.deleteKategori(idKategori)
                _kategorituiState.value = DetailKategoriUiState.Error("Data Penerbit telah dihapus.")
            } catch (e: Exception) {
                _kategorituiState.value = DetailKategoriUiState.Error(e.localizedMessage ?: "Gagal menghapus data.")
            }
        }
    }
}
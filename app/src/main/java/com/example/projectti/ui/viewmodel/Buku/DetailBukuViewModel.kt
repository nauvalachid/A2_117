package com.example.projectti.ui.viewmodel.Buku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Buku
import com.example.projectti.model.Penerbit
import com.example.projectti.repository.BukuRepository
import com.example.projectti.repository.PenerbitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailBukuUiState{
    object Loading:DetailBukuUiState()
    data class Success(val buku: Buku):DetailBukuUiState()
    data class Error(val message: String) : DetailBukuUiState()
}

class DetailBukuViewModel(private val bk: BukuRepository): ViewModel() {
    private val _bukuuiState = MutableStateFlow<DetailBukuUiState>(DetailBukuUiState.Loading)
    val bukuuiState: StateFlow<DetailBukuUiState> = _bukuuiState.asStateFlow()

    fun getBukuById(idBuku: Int) {
        viewModelScope.launch {
            try {
                val buku = bk.getBukuById(idBuku)
                _bukuuiState.value = DetailBukuUiState.Success(buku)
            } catch (e: Exception) {
                _bukuuiState.value = DetailBukuUiState.Error(e.localizedMessage ?: "Terjadi Kesalahan.")
            }
        }
    }

    fun deleteBk(idBuku: Int) {
        viewModelScope.launch {
            try {
                bk.deleteBuku(idBuku)
                _bukuuiState.value = DetailBukuUiState.Error("Data Penerbit telah dihapus.")
            } catch (e: Exception) {
                _bukuuiState.value = DetailBukuUiState.Error(e.localizedMessage ?: "Gagal menghapus data.")
            }
        }
    }
}
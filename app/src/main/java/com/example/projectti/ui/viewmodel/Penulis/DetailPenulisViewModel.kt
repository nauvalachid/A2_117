package com.example.projectti.ui.viewmodel.Penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Penulis
import com.example.projectti.repository.PenulisRepository
import com.example.projectti.ui.view.Penulis.DestinasiDetailPenulis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class DetailPenulisUiState{
    object Loading:DetailPenulisUiState()
    data class Success(val penulis: Penulis):DetailPenulisUiState()
    data class Error(val message: String) : DetailPenulisUiState()
}

class DetailPenulisViewModel(private val pnls: PenulisRepository):ViewModel() {
    private val _penulisuiState = MutableStateFlow<DetailPenulisUiState>(DetailPenulisUiState.Loading)
    val penulisuiState: StateFlow<DetailPenulisUiState> = _penulisuiState.asStateFlow()

    fun getPenulisById(idPenulis: Int) {
        viewModelScope.launch {
            try {
                val penulis = pnls.getPenulisById(idPenulis)
                _penulisuiState.value = DetailPenulisUiState.Success(penulis)
            } catch (e: Exception) {
                _penulisuiState.value = DetailPenulisUiState.Error(e.localizedMessage ?: "Terjadi Kesalahan.")
            }
        }
    }

    fun deletePnls(idPenulis: Int) {
        viewModelScope.launch {
            try {
                pnls.deletePenulis(idPenulis)
                _penulisuiState.value = DetailPenulisUiState.Error("Data Penulis telah dihapus.")
            } catch (e: Exception) {
                _penulisuiState.value = DetailPenulisUiState.Error(e.localizedMessage ?: "Gagal menghapus data.")
            }
        }
    }
}
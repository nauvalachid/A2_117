package com.example.projectti.ui.viewmodel.Penerbit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Penerbit
import com.example.projectti.model.Penulis
import com.example.projectti.repository.PenerbitRepository
import com.example.projectti.repository.PenulisRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailPenerbitUiState{
    object Loading:DetailPenerbitUiState()
    data class Success(val penerbit: Penerbit):DetailPenerbitUiState()
    data class Error(val message: String) : DetailPenerbitUiState()
}

class DetailPenerbitViewModel(private val pnrbt: PenerbitRepository): ViewModel() {
    private val _penerbituiState = MutableStateFlow<DetailPenerbitUiState>(DetailPenerbitUiState.Loading)
    val penerbituiState: StateFlow<DetailPenerbitUiState> = _penerbituiState.asStateFlow()

    fun getPenerbitById(idPenerbit: Int) {
        viewModelScope.launch {
            try {
                val penerbit = pnrbt.getPenerbitById(idPenerbit)
                _penerbituiState.value = DetailPenerbitUiState.Success(penerbit)
            } catch (e: Exception) {
                _penerbituiState.value = DetailPenerbitUiState.Error(e.localizedMessage ?: "Terjadi Kesalahan.")
            }
        }
    }

    fun deletePnrbt(idPenerbit: Int) {
        viewModelScope.launch {
            try {
                pnrbt.deletePenerbit(idPenerbit)
                _penerbituiState.value = DetailPenerbitUiState.Error("Data Penerbit telah dihapus.")
            } catch (e: Exception) {
                _penerbituiState.value = DetailPenerbitUiState.Error(e.localizedMessage ?: "Gagal menghapus data.")
            }
        }
    }
}
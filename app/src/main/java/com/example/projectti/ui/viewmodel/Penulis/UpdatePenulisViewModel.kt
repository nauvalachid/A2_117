package com.example.projectti.ui.viewmodel.Penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Penulis
import com.example.projectti.repository.PenulisRepository
import kotlinx.coroutines.launch

class UpdatePenulisViewModel(private val pnls: PenulisRepository) : ViewModel(){
    var penulisuiState by mutableStateOf(UpdatePenulisUiState())
        private set

    fun loadPenulis(idPenulis: Int){
        viewModelScope.launch {
            try{
                val penulis = pnls.getPenulisById(idPenulis)
                penulisuiState = UpdatePenulisUiState(updatePenulisUiEvent = penulis.toUpdatePenulisUiEvent())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun updatePenulisState(updatePenulisUiEvent: UpdatePenulisUiEvent){
        penulisuiState = UpdatePenulisUiState(updatePenulisUiEvent = updatePenulisUiEvent)
    }

    suspend fun updatePenulis(idPenulis: Int) {
        viewModelScope.launch {
            try {
                pnls.updatePenulis(idPenulis,penulisuiState.updatePenulisUiEvent.toPenulis())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class UpdatePenulisUiState(
    val updatePenulisUiEvent : UpdatePenulisUiEvent = UpdatePenulisUiEvent()
)

data class UpdatePenulisUiEvent(
    val idPenulis: Int = 0,
    val namaPenulis: String ="",
    val biografi: String ="",
    val kontak: String ="",
)

fun UpdatePenulisUiEvent.toPenulis(): Penulis = Penulis(
    idPenulis = idPenulis,
    namaPenulis = namaPenulis,
    biografi = biografi,
    kontak = kontak
)

fun Penulis.toUpdatePenulisUiEvent(): UpdatePenulisUiEvent = UpdatePenulisUiEvent(
    idPenulis = idPenulis,
    namaPenulis = namaPenulis,
    biografi = biografi,
    kontak = kontak
)
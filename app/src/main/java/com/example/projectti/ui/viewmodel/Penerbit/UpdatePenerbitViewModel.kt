package com.example.projectti.ui.viewmodel.Penerbit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Penerbit
import com.example.projectti.model.Penulis
import com.example.projectti.repository.PenerbitRepository
import com.example.projectti.repository.PenulisRepository
import kotlinx.coroutines.launch

class UpdatePenerbitViewModel(private val pnrbt: PenerbitRepository) : ViewModel(){
    var penerbituiState by mutableStateOf(UpdatePenerbitUiState())
        private set

    fun loadPenerbit(idPenerbit: Int){
        viewModelScope.launch {
            try{
                val penerbit = pnrbt.getPenerbitById(idPenerbit)
                penerbituiState = UpdatePenerbitUiState(updatePenerbitUiEvent = penerbit.toUpdatePenerbitUiEvent())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun updatePenerbitState(updatePenerbitUiEvent: UpdatePenerbitUiEvent){
        penerbituiState = UpdatePenerbitUiState(updatePenerbitUiEvent = updatePenerbitUiEvent)
    }

    suspend fun updatePenerbit(idPenerbit: Int) {
        viewModelScope.launch {
            try {
                pnrbt.updatePenerbit(idPenerbit,penerbituiState.updatePenerbitUiEvent.toPenerbit())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class UpdatePenerbitUiState(
    val updatePenerbitUiEvent : UpdatePenerbitUiEvent = UpdatePenerbitUiEvent()
)

data class UpdatePenerbitUiEvent(
    val idPenerbit: Int = 0,
    val namaPenerbit: String ="",
    val alamatPenerbit: String ="",
    val teleponPenerbit: String ="",
)

fun UpdatePenerbitUiEvent.toPenerbit(): Penerbit = Penerbit(
    idPenerbit = idPenerbit,
    namaPenerbit = namaPenerbit,
    alamatPenerbit = alamatPenerbit,
    teleponPenerbit = teleponPenerbit
)

fun Penerbit.toUpdatePenerbitUiEvent(): UpdatePenerbitUiEvent = UpdatePenerbitUiEvent(
    idPenerbit = idPenerbit,
    namaPenerbit = namaPenerbit,
    alamatPenerbit = alamatPenerbit,
    teleponPenerbit = teleponPenerbit
)
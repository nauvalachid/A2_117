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

class InsertPenerbitViewModel(private val pnrbt: PenerbitRepository): ViewModel(){
    var penerbituiState by mutableStateOf(InsertPenerbitUiState())
        private set
    fun updateInsertpnrbtState(insertPenerbitUiEvent:InsertPenerbitUiEvent){
        penerbituiState = InsertPenerbitUiState(insertPenerbitUiEvent = insertPenerbitUiEvent)
    }

    suspend fun insertPnrbt(){
        viewModelScope.launch {
            try {
                pnrbt.insertPenerbit(penerbituiState.insertPenerbitUiEvent.toPnrbt())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertPenerbitUiState(
    val insertPenerbitUiEvent: InsertPenerbitUiEvent = InsertPenerbitUiEvent()
)

data class InsertPenerbitUiEvent(
    val idPenerbit: Int = 0 ,
    val namaPenerbit: String ="",
    val alamatPenerbit: String ="",
    val teleponPenerbit: String ="",
)

fun InsertPenerbitUiEvent.toPnrbt(): Penerbit = Penerbit(
    idPenerbit = idPenerbit,
    namaPenerbit = namaPenerbit,
    alamatPenerbit = alamatPenerbit,
    teleponPenerbit = teleponPenerbit,
)

fun Penerbit.toUiStatePnrbt(): InsertPenerbitUiState = InsertPenerbitUiState(
    insertPenerbitUiEvent = toInsertPenerbitUiEvent()
)

fun Penerbit.toInsertPenerbitUiEvent(): InsertPenerbitUiEvent = InsertPenerbitUiEvent(
    idPenerbit = idPenerbit,
    namaPenerbit = namaPenerbit,
    alamatPenerbit = alamatPenerbit,
    teleponPenerbit = alamatPenerbit,
)
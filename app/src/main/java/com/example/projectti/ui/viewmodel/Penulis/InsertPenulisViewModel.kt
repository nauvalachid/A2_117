package com.example.projectti.ui.viewmodel.Penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Penulis
import com.example.projectti.repository.PenulisRepository
import kotlinx.coroutines.launch

class InsertPenulisViewModel(private val pnls: PenulisRepository): ViewModel(){
    var penulisuiState by mutableStateOf(InsertPenulisUiState())
        private set
    fun updateInsertpnlsState(insertPenulisUiEvent:InsertPenulisUiEvent){
        penulisuiState = InsertPenulisUiState(insertPenulisUiEvent = insertPenulisUiEvent)
    }

    suspend fun insertPnls(){
        viewModelScope.launch {
            try {
                pnls.insertPenulis(penulisuiState.insertPenulisUiEvent.toPnls())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertPenulisUiState(
    val insertPenulisUiEvent: InsertPenulisUiEvent = InsertPenulisUiEvent()
)

data class InsertPenulisUiEvent(
    val idPenulis: Int = 0 ,
    val namaPenulis: String ="",
    val biografi: String ="",
    val kontak: String ="",
)

fun InsertPenulisUiEvent.toPnls(): Penulis = Penulis(
    idPenulis = idPenulis,
    namaPenulis = namaPenulis,
    biografi = biografi,
    kontak = kontak,
)

fun Penulis.toUiStatePnls(): InsertPenulisUiState = InsertPenulisUiState(
    insertPenulisUiEvent = toInsertPenulisUiEvent()
)

fun Penulis.toInsertPenulisUiEvent(): InsertPenulisUiEvent = InsertPenulisUiEvent(
    idPenulis = idPenulis,
    namaPenulis = namaPenulis,
    biografi = biografi,
    kontak = kontak,
)
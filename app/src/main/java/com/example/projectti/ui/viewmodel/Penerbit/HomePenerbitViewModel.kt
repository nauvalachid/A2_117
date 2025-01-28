package com.example.projectti.ui.viewmodel.Penerbit

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
import okio.IOException
import retrofit2.HttpException

sealed class HomePenerbitUiState {
    data class Success(val penerbit: List<Penerbit>): HomePenerbitUiState()
    object Error: HomePenerbitUiState()
    object Loading: HomePenerbitUiState()
}

class HomePenerbitViewModel(private val pnrbt: PenerbitRepository): ViewModel(){
    var pnrbtUiState: HomePenerbitUiState by mutableStateOf(HomePenerbitUiState.Loading)
        private set

    init {
        getPnrbt()
    }

    fun getPnrbt(){
        viewModelScope.launch {
            pnrbtUiState = HomePenerbitUiState.Loading
            pnrbtUiState = try {
                HomePenerbitUiState.Success(pnrbt.getPenerbit())
            } catch (e: IOException) {
                HomePenerbitUiState.Error
            } catch (e: HttpException) {
                HomePenerbitUiState.Error
            }
        }
    }

    fun deletepnrbt(idPenerbit:Int) {
        viewModelScope.launch {
            try {
                pnrbt.deletePenerbit(idPenerbit)
            } catch (e:IOException){
                HomePenerbitUiState.Error
            } catch (e: HttpException){
                HomePenerbitUiState.Error
            }
        }
    }
}
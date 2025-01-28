package com.example.projectti.ui.viewmodel.Penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Penulis
import com.example.projectti.repository.PenulisRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomePenulisUiState {
    data class Success(val penulis: List<Penulis>): HomePenulisUiState()
    object Error:HomePenulisUiState()
    object Loading:HomePenulisUiState()
}

class HomePenulisViewModel(private val pnls: PenulisRepository): ViewModel(){
    var PnlsUiState: HomePenulisUiState by mutableStateOf(HomePenulisUiState.Loading)
        private set

    init {
        getPnls()
    }

    fun getPnls(){
        viewModelScope.launch {
            PnlsUiState = HomePenulisUiState.Loading
            PnlsUiState = try {
                HomePenulisUiState.Success(pnls.getPenulis())
            } catch (e: IOException) {
                HomePenulisUiState.Error
            } catch (e: HttpException) {
                HomePenulisUiState.Error
            }
        }
    }

    fun deletePnls(idPenulis:Int) {
        viewModelScope.launch {
            try {
                pnls.deletePenulis(idPenulis)
            } catch (e:IOException){
                HomePenulisUiState.Error
            } catch (e: HttpException){
                HomePenulisUiState.Error
            }
        }
    }
}
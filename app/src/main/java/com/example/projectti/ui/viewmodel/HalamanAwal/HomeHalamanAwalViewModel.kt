package com.example.projectti.ui.viewmodel.HalamanAwal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Buku
import com.example.projectti.model.Kategori
import com.example.projectti.repository.BukuRepository
import com.example.projectti.repository.KategoriRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeHalamanAwalUiState {
    data class Success(val buku: List<Buku>): HomeHalamanAwalUiState()
    object Error: HomeHalamanAwalUiState()
    object Loading: HomeHalamanAwalUiState()
}

class HomeHalamanAwalViewModel(private val hlmn: BukuRepository): ViewModel() {
    var hlmnUiState: HomeHalamanAwalUiState by mutableStateOf(HomeHalamanAwalUiState.Loading)
        private set

    init {
        getHlmn()
    }

    fun getHlmn() {
        viewModelScope.launch {
            hlmnUiState = HomeHalamanAwalUiState.Loading
            hlmnUiState = try {
                HomeHalamanAwalUiState.Success(hlmn.getBuku())
            } catch (e: IOException) {
                HomeHalamanAwalUiState.Error
            } catch (e: HttpException) {
                HomeHalamanAwalUiState.Error
            }
        }
    }
}

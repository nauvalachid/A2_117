package com.example.projectti.ui.viewmodel.Kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectti.model.Kategori
import com.example.projectti.repository.KategoriRepository
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

sealed class HomeKategoriUiState {
    data class Success(val kategori: List<Kategori>): HomeKategoriUiState()
    object Error: HomeKategoriUiState()
    object Loading: HomeKategoriUiState()
}

class HomeKategoriViewModel(private val ktgr: KategoriRepository): ViewModel(){
    var ktgrUiState: HomeKategoriUiState by mutableStateOf(HomeKategoriUiState.Loading)
        private set

    init {
        getKtgr()
    }

    fun getKtgr(){
        viewModelScope.launch {
            ktgrUiState = HomeKategoriUiState.Loading
            ktgrUiState = try {
                HomeKategoriUiState.Success(ktgr.getKategori())
            } catch (e: IOException) {
                HomeKategoriUiState.Error
            } catch (e: HttpException) {
                HomeKategoriUiState.Error
            }
        }
    }

    fun deletektgr(idKategori:Int) {
        viewModelScope.launch {
            try {
                ktgr.deleteKategori(idKategori)
            } catch (e:IOException){
                HomeKategoriUiState.Error
            } catch (e: HttpException){
                HomeKategoriUiState.Error
            }
        }
    }
}
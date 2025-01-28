package com.example.projectti.ui.viewmodel.Kategori

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectti.BukuApplications
import com.example.projectti.ui.viewmodel.Penerbit.DetailPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penerbit.UpdatePenerbitViewModel
import com.example.projectti.ui.viewmodel.Penulis.HomePenulisViewModel
import com.example.projectti.ui.viewmodel.Penulis.aplikasiBuku

object PenyediaKategoriViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeKategoriViewModel(
                aplikasiBuku().container.kategoriRepository
            )
        }
        initializer {
            InsertKategoriViewModel(
                aplikasiBuku().container.kategoriRepository
            )
        }
        initializer {
            DetailKategoriViewModel(
                aplikasiBuku().container.kategoriRepository
            )
        }
        initializer {
            UpdateKategoriViewModel(
                aplikasiBuku().container.kategoriRepository
            )
        }
    }
}

fun CreationExtras.aplikasiBuku(): BukuApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as BukuApplications)
package com.example.projectti.ui.viewmodel.HalamanAwal

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectti.BukuApplications
import com.example.projectti.ui.viewmodel.Kategori.HomeKategoriViewModel
import com.example.projectti.ui.viewmodel.Kategori.InsertKategoriViewModel

object PenyediaHalamanAwalViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeHalamanAwalViewModel(
                aplikasiBuku().container.bukuRepository
            )
        }

    }
}

fun CreationExtras.aplikasiBuku(): BukuApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as BukuApplications)
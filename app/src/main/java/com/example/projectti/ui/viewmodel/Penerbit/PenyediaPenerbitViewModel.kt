package com.example.projectti.ui.viewmodel.Penerbit

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectti.BukuApplications
import com.example.projectti.ui.viewmodel.Kategori.HomeKategoriViewModel
import com.example.projectti.ui.viewmodel.Penulis.DetailPenulisViewModel
import com.example.projectti.ui.viewmodel.Penulis.UpdatePenulisViewModel
import com.example.projectti.ui.viewmodel.Penulis.aplikasiBuku

object PenyediaPenerbitViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomePenerbitViewModel(
                aplikasiBuku().container.penerbitRepository
            )
        }
        initializer {
            InsertPenerbitViewModel(
                aplikasiBuku().container.penerbitRepository
            )
        }
        initializer {
            DetailPenerbitViewModel(
                aplikasiBuku().container.penerbitRepository
            )
        }
        initializer {
            UpdatePenerbitViewModel(
                aplikasiBuku().container.penerbitRepository
            )
        }
    }
}

fun CreationExtras.aplikasiBuku(): BukuApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as BukuApplications)
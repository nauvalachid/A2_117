package com.example.projectti.ui.viewmodel.Penulis

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectti.BukuApplications

object PenyediaPenulisViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomePenulisViewModel(
                aplikasiBuku().container.penulisRepository
            )
        }
        initializer {
            InsertPenulisViewModel(
                aplikasiBuku().container.penulisRepository
            )
        }
        initializer {
            DetailPenulisViewModel(
                aplikasiBuku().container.penulisRepository
            )
        }
        initializer {
            UpdatePenulisViewModel(
                aplikasiBuku().container.penulisRepository
            )
        }
    }
}

fun CreationExtras.aplikasiBuku(): BukuApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as BukuApplications)
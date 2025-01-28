package com.example.projectti.ui.viewmodel.Buku

import com.example.projectti.BukuApplications
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

object PenyediaBukuViewModel {
    val Factory = viewModelFactory {
        initializer {
            InsertBukuViewModel(
                aplikasiBuku().container.bukuRepository
            )
        }
    }
}

fun CreationExtras.aplikasiBuku(): BukuApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as BukuApplications)
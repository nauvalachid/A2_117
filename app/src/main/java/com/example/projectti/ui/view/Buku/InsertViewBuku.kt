package com.example.projectti.ui.view.Buku

import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Alignment
import com.example.projectti.ui.viewmodel.Buku.InsertBukuUiEvent
import com.example.projectti.ui.viewmodel.Buku.InsertBukuUiState
import com.example.projectti.ui.viewmodel.Buku.InsertBukuViewModel
import com.example.projectti.ui.viewmodel.Buku.PenyediaBukuViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectti.Navigation.DestinasiNavigasi
import com.example.projectti.ui.viewmodel.Kategori.HomeKategoriViewModel
import com.example.projectti.ui.viewmodel.Kategori.InsertKategoriViewModel
import com.example.projectti.ui.viewmodel.Kategori.PenyediaKategoriViewModel
import com.example.projectti.ui.viewmodel.Penerbit.HomePenerbitViewModel
import com.example.projectti.ui.viewmodel.Penerbit.PenyediaPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penulis.HomePenulisViewModel
import com.example.projectti.ui.viewmodel.Penulis.PenyediaPenulisViewModel
import kotlinx.coroutines.launch

object DestinasiEntryBuku : DestinasiNavigasi {
    override val route = "item_entryBuku"
    override val titleRes = "Entry Bk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBkScreen(
    navigateBack:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertBukuViewModel = viewModel(factory = PenyediaBukuViewModel.Factory),
    viewModelKategori: HomeKategoriViewModel = viewModel(factory = PenyediaKategoriViewModel.Factory),
    viewModelPenerbit: HomePenerbitViewModel = viewModel(factory = PenyediaPenerbitViewModel.Factory),
    viewModelPenulis: HomePenulisViewModel = viewModel(factory = PenyediaPenulisViewModel.Factory),
){
    val coroutineScope = rememberCoroutineScope()
    val kategoriUiState = viewModelKategori.ktgrUiState
    val penerbitUiState = viewModelPenerbit.pnrbtUiState
    val penulisUiState = viewModelPenulis.PnlsUiState
    Scaffold (
        modifier = modifier,
    ) { innerPadding ->
        EntryBodyBuku(
            insertBukuUiState = viewModel.bukuuiState,
            onBukuValueChange = viewModel::updateInsertbkState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertBk()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
        )
    }
}

@Composable
fun EntryBodyBuku(
    insertBukuUiState: InsertBukuUiState,
    onBukuValueChange: (InsertBukuUiEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier=modifier.padding(12.dp)
    ) {
        FormInputBuku(
            insertBukuUiEvent = insertBukuUiState.insertBukuUiEvent,
            onValueChange = onBukuValueChange,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputBuku(
    insertBukuUiEvent: InsertBukuUiEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertBukuUiEvent)->Unit={},
    enabled:Boolean = true,
    viewModelKlienViewModel: HomeKlienViewModel,
    viewModelLokasiViewModel: HomeLokasiViewModel
){
    Column (
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertBukuUiEvent.namaBuku,
            onValueChange = {onValueChange(insertBukuUiEvent.copy(namaBuku = it))},
            label = { Text("Nama Buku") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertBukuUiEvent.deskripsiBuku,
            onValueChange = {onValueChange(insertBukuUiEvent.copy(deskripsiBuku = it))},
            label = { Text("Deskripsi Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertBukuUiEvent.tanggalTerbit,
            onValueChange = {onValueChange(insertBukuUiEvent.copy(tanggalTerbit = it))},
            label = { Text("Tanggal Terbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Text(
            text = "Status Buku",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        Column {
            val options = listOf("Tersedia", "Habis", "Dipesan")
            options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.RadioButton(
                        selected = insertBukuUiEvent.statusBuku == option,
                        onClick = { onValueChange(insertBukuUiEvent.copy(statusBuku = option)) },
                        enabled = enabled
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        if (enabled){
            Text(
                text = "Isi Semua Data!",
                modifier= Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
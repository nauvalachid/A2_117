package com.example.projectti.ui.view.Kategori

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
import com.example.projectti.ui.viewmodel.Kategori.InsertKategoriUiEvent
import com.example.projectti.ui.viewmodel.Kategori.InsertKategoriUiState
import com.example.projectti.ui.viewmodel.Kategori.InsertKategoriViewModel
import com.example.projectti.ui.viewmodel.Kategori.PenyediaKategoriViewModel
import com.example.projectti.ui.viewmodel.Penerbit.InsertPenerbitUiEvent
import com.example.projectti.ui.viewmodel.Penerbit.InsertPenerbitUiState
import com.example.projectti.ui.viewmodel.Penerbit.InsertPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penerbit.PenyediaPenerbitViewModel
import kotlinx.coroutines.launch

object DestinasiEntryKategori : DestinasiNavigasi {
    override val route = "item_entryKategori"
    override val titleRes = "Entry Ktgr"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryKtgrScreen(
    navigateBack:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertKategoriViewModel = viewModel(factory = PenyediaKategoriViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        modifier = modifier,
    ) { innerPadding ->
        EntryBodyKategori(
            insertKategoriUiState = viewModel.kategoriuiState,
            onKategoriValueChange = viewModel::updateInsertktgrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertKtgr()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }
}

@Composable
fun EntryBodyKategori(
    insertKategoriUiState: InsertKategoriUiState,
    onKategoriValueChange: (InsertKategoriUiEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier=modifier.padding(12.dp)
    ) {
        FormInputKategori(
            insertKategoriUiEvent = insertKategoriUiState.insertKategoriUiEvent,
            onValueChange = onKategoriValueChange,
            modifier = Modifier.fillMaxWidth()
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
fun FormInputKategori(
    insertKategoriUiEvent: InsertKategoriUiEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertKategoriUiEvent)->Unit={},
    enabled:Boolean = true
){
    Column (
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertKategoriUiEvent.namaKategori,
            onValueChange = {onValueChange(insertKategoriUiEvent.copy(namaKategori = it))},
            label = { Text("Nama Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertKategoriUiEvent.deskripsiKategori,
            onValueChange = {onValueChange(insertKategoriUiEvent.copy(deskripsiKategori = it))},
            label = { Text("Deskripsi Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
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
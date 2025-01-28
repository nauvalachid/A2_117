package com.example.projectti.ui.view.Penulis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectti.Navigation.DestinasiNavigasi
import com.example.projectti.ui.viewmodel.Penulis.InsertPenulisUiEvent
import com.example.projectti.ui.viewmodel.Penulis.InsertPenulisUiState
import com.example.projectti.ui.viewmodel.Penulis.InsertPenulisViewModel
import com.example.projectti.ui.viewmodel.Penulis.PenyediaPenulisViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPenulis : DestinasiNavigasi {
    override val route = "item_entryPenulis"
    override val titleRes = "Entry Pnls"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPnlsScreen(
    navigateBack:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenulisViewModel = viewModel(factory = PenyediaPenulisViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        modifier = modifier,
    ) { innerPadding ->
        EntryBodyPenulis(
            insertPenulisUiState = viewModel.penulisuiState,
            onPenulisValueChange = viewModel::updateInsertpnlsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPnls()
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
fun EntryBodyPenulis(
    insertPenulisUiState: InsertPenulisUiState,
    onPenulisValueChange: (InsertPenulisUiEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier=modifier.padding(12.dp)
    ) {
        FormInputPenulis(
            insertPenulisUiEvent = insertPenulisUiState.insertPenulisUiEvent,
            onValueChange = onPenulisValueChange,
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
fun FormInputPenulis(
    insertPenulisUiEvent: InsertPenulisUiEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertPenulisUiEvent)->Unit={},
    enabled:Boolean = true
){
    Column (
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertPenulisUiEvent.namaPenulis,
            onValueChange = {onValueChange(insertPenulisUiEvent.copy(namaPenulis=it))},
            label = { Text("Nama Penulis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPenulisUiEvent.biografi,
            onValueChange = {onValueChange(insertPenulisUiEvent.copy(biografi = it))},
            label = { Text("Biografi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPenulisUiEvent.kontak,
            onValueChange = {onValueChange(insertPenulisUiEvent.copy(kontak=it))},
            label = { Text("Kontak") },
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
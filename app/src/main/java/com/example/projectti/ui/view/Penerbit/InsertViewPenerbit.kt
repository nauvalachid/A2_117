package com.example.projectti.ui.view.Penerbit

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
import com.example.projectti.ui.viewmodel.Penerbit.InsertPenerbitUiEvent
import com.example.projectti.ui.viewmodel.Penerbit.InsertPenerbitUiState
import com.example.projectti.ui.viewmodel.Penerbit.InsertPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penerbit.PenyediaPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penulis.InsertPenulisUiEvent
import com.example.projectti.ui.viewmodel.Penulis.InsertPenulisUiState
import com.example.projectti.ui.viewmodel.Penulis.InsertPenulisViewModel
import com.example.projectti.ui.viewmodel.Penulis.PenyediaPenulisViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPenerbit : DestinasiNavigasi {
    override val route = "item_entryPenerbit"
    override val titleRes = "Entry Pnrbt"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPnrbtScreen(
    navigateBack:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenerbitViewModel = viewModel(factory = PenyediaPenerbitViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        modifier = modifier,
    ) { innerPadding ->
        EntryBodyPenerbit(
            insertPenerbitUiState = viewModel.penerbituiState,
            onPenerbitValueChange = viewModel::updateInsertpnrbtState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPnrbt()
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
fun EntryBodyPenerbit(
    insertPenerbitUiState: InsertPenerbitUiState,
    onPenerbitValueChange: (InsertPenerbitUiEvent)->Unit,
    onSaveClick:()->Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier=modifier.padding(12.dp)
    ) {
        FormInputPenerbit(
            insertPenerbitUiEvent = insertPenerbitUiState.insertPenerbitUiEvent,
            onValueChange = onPenerbitValueChange,
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
fun FormInputPenerbit(
    insertPenerbitUiEvent: InsertPenerbitUiEvent,
    modifier: Modifier = Modifier,
    onValueChange:(InsertPenerbitUiEvent)->Unit={},
    enabled:Boolean = true
){
    Column (
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertPenerbitUiEvent.namaPenerbit,
            onValueChange = {onValueChange(insertPenerbitUiEvent.copy(namaPenerbit=it))},
            label = { Text("Nama Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPenerbitUiEvent.alamatPenerbit,
            onValueChange = {onValueChange(insertPenerbitUiEvent.copy(alamatPenerbit = it))},
            label = { Text("Alamat Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPenerbitUiEvent.teleponPenerbit,
            onValueChange = {onValueChange(insertPenerbitUiEvent.copy(teleponPenerbit = it))},
            label = { Text("Telepon Penerbit") },
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
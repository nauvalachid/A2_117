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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.projectti.ui.viewmodel.Widget.DynamicSelectedTextField
import kotlinx.coroutines.launch

object DestinasiEntryBuku : DestinasiNavigasi {
    override val route = "item_entryBuku"
    override val titleRes = "Entry Bk"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBkScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var bukuUiState by remember { mutableStateOf(InsertBukuUiEvent()) }

    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            FormInputBuku(
                insertBukuUiEvent = bukuUiState,
                onValueChange = { bukuUiState = it },
                enabled = true
            )
            Button(
                onClick = {
                    coroutineScope.launch {
                        // Simpan logika
                        navigateBack()
                    }
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Simpan")
            }
        }
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
    onValueChange: (InsertBukuUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertBukuUiEvent.namaBuku,
            onValueChange = { onValueChange(insertBukuUiEvent.copy(namaBuku = it)) },
            label = { Text("Nama Buku") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertBukuUiEvent.deskripsiBuku,
            onValueChange = { onValueChange(insertBukuUiEvent.copy(deskripsiBuku = it)) },
            label = { Text("Deskripsi Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertBukuUiEvent.tanggalTerbit,
            onValueChange = { onValueChange(insertBukuUiEvent.copy(tanggalTerbit = it)) },
            label = { Text("Tanggal Terbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        DynamicSelectedTextField(
            selectedId = insertBukuUiEvent.idKategori,
            options = listOf(
                1 to "Fiksi",
                2 to "Non-Fiksi",
                3 to "Sejarah",
                4 to "Teknologi"
            ),
            label = "Pilih Kategori",
            onValueChangedEvent = { idKategori, _ ->
                onValueChange(insertBukuUiEvent.copy(idKategori = idKategori))
            },
            modifier = Modifier.fillMaxWidth()
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
        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}

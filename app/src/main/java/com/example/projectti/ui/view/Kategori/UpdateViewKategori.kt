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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectti.Navigation.DestinasiNavigasi
import com.example.projectti.ui.viewmodel.Kategori.PenyediaKategoriViewModel
import com.example.projectti.ui.viewmodel.Kategori.UpdateKategoriUiEvent
import com.example.projectti.ui.viewmodel.Kategori.UpdateKategoriUiState
import com.example.projectti.ui.viewmodel.Kategori.UpdateKategoriViewModel
import com.example.projectti.ui.viewmodel.Penerbit.PenyediaPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penerbit.UpdatePenerbitUiEvent
import com.example.projectti.ui.viewmodel.Penerbit.UpdatePenerbitUiState
import com.example.projectti.ui.viewmodel.Penerbit.UpdatePenerbitViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateKategori : DestinasiNavigasi {
    override val route = "item_updatekategori"
    override val titleRes = "Update Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateViewKategori(
    idKategori: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateKategoriViewModel = viewModel(factory = PenyediaKategoriViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect (idKategori){
        viewModel.loadKategori(idKategori)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        UpdateBodyKategori(
            updateKategoriUiState = viewModel.kategoriuiState,
            onKategoriValueChange = viewModel::updateKategoriState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateKategori(idKategori)
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
fun UpdateBodyKategori(
    updateKategoriUiState: UpdateKategoriUiState,
    onKategoriValueChange: (UpdateKategoriUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateKategoriUiEvent = updateKategoriUiState.updateKategoriUiEvent,
            onValueChange = onKategoriValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    updateKategoriUiEvent: UpdateKategoriUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateKategoriUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateKategoriUiEvent.namaKategori,
            onValueChange = {onValueChange(updateKategoriUiEvent.copy(namaKategori=it))},
            label = { Text("Nama Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateKategoriUiEvent.deskripsiKategori,
            onValueChange = {onValueChange(updateKategoriUiEvent.copy(deskripsiKategori = it))},
            label = { Text("alamat Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
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
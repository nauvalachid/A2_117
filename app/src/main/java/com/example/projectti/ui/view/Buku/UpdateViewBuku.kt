package com.example.projectti.ui.view.Buku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectti.Navigation.DestinasiNavigasi
import com.example.projectti.ui.viewmodel.Buku.PenyediaBukuViewModel
import com.example.projectti.ui.viewmodel.Buku.UpdateBukuUiEvent
import com.example.projectti.ui.viewmodel.Buku.UpdateBukuUiState
import com.example.projectti.ui.viewmodel.Buku.UpdateBukuViewModel
import com.example.projectti.ui.viewmodel.Kategori.HomeKategoriUiState
import com.example.projectti.ui.viewmodel.Kategori.HomeKategoriViewModel
import com.example.projectti.ui.viewmodel.Kategori.PenyediaKategoriViewModel
import com.example.projectti.ui.viewmodel.Kategori.UpdateKategoriUiEvent
import com.example.projectti.ui.viewmodel.Kategori.UpdateKategoriUiState
import com.example.projectti.ui.viewmodel.Kategori.UpdateKategoriViewModel
import com.example.projectti.ui.viewmodel.Penerbit.HomePenerbitUiState
import com.example.projectti.ui.viewmodel.Penerbit.HomePenerbitViewModel
import com.example.projectti.ui.viewmodel.Penerbit.PenyediaPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penulis.HomePenulisUiState
import com.example.projectti.ui.viewmodel.Penulis.HomePenulisViewModel
import com.example.projectti.ui.viewmodel.Penulis.PenyediaPenulisViewModel
import com.example.projectti.ui.viewmodel.Widget.DynamicSelectedTextField
import kotlinx.coroutines.launch

object DestinasiUpdateBuku : DestinasiNavigasi {
    override val route = "item_updatekategori"
    override val titleRes = "Update Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateViewBuku(
    idBuku: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateBukuViewModel = viewModel(factory = PenyediaBukuViewModel.Factory),
    viewModelKtg: HomeKategoriViewModel = viewModel(factory = PenyediaKategoriViewModel.Factory),
    viewModePnb: HomePenerbitViewModel = viewModel(factory = PenyediaPenerbitViewModel.Factory),
    viewModelPns: HomePenulisViewModel = viewModel(factory = PenyediaPenulisViewModel.Factory)
){
    val ktgrUiState = viewModelKtg.ktgrUiState
    val pnrbtUiState = viewModePnb.pnrbtUiState
    val PnlsUiState = viewModelPns.PnlsUiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect (idBuku){
        viewModel.loadBuku(idBuku)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        UpdateBodyBuku(
            updateBukuUiState = viewModel.bukuuiState,
            onBukuValueChange = viewModel::updateBukuState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateBuku(idBuku)
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
fun UpdateBodyBuku(
    updateBukuUiState: UpdateBukuUiState,
    onBukuValueChange: (UpdateBukuUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateBukuUiEvent = updateBukuUiState.updateBukuUiEvent,
            onValueChange = onBukuValueChange,
            modifier = Modifier.fillMaxWidth(),
            viewModelPenulisViewModel = viewModel(),
            viewModelPenerbitViewModel = viewModel(),
            viewModelKategoriViewModel = viewModel()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            enabled = updateBukuUiState.updateBukuUiEvent.idKategori.isNotEmpty()
                    && updateBukuUiState.updateBukuUiEvent.idPenulis.isNotEmpty()
                    && updateBukuUiState.updateBukuUiEvent.idPenerbit.isNotEmpty()
        ) {
            Text(text = "Update")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    updateBukuUiEvent: UpdateBukuUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateBukuUiEvent) -> Unit = {},
    enabled: Boolean = true,
    viewModelPenulisViewModel: HomePenulisViewModel,
    viewModelPenerbitViewModel: HomePenerbitViewModel,
    viewModelKategoriViewModel: HomeKategoriViewModel
) {
    val pnlsUiState = viewModelPenulisViewModel.PnlsUiState
    val pnrbtUiState = viewModelPenerbitViewModel.pnrbtUiState
    val ktgrUiState = viewModelKategoriViewModel.ktgrUiState

    when (pnlsUiState) {
        is HomePenulisUiState.Loading -> {
            // Menampilkan indikator loading
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is HomePenulisUiState.Error -> {
            // Menampilkan pesan error
            Text("Gagal mengambil data Penulis", color = MaterialTheme.colorScheme.error)
        }

        is HomePenulisUiState.Success -> {
            val penulisList = pnlsUiState.penulis
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = updateBukuUiEvent.namaBuku,
                    onValueChange = { onValueChange(updateBukuUiEvent.copy(namaBuku = it)) },
                    label = { Text("Nama Buku") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = updateBukuUiEvent.deskripsiBuku,
                    onValueChange = { onValueChange(updateBukuUiEvent.copy(deskripsiBuku = it)) },
                    label = { Text("Deskripsi Kategori") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )
                OutlinedTextField(
                    value = updateBukuUiEvent.tanggalTerbit,
                    onValueChange = { onValueChange(updateBukuUiEvent.copy(tanggalTerbit = it)) },
                    label = { Text("Tanggal Terbit") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true
                )

                DynamicSelectedTextField(
                    selectedValue = updateBukuUiEvent.idPenulis.toString(),
                    options = penulisList.map { it.idPenulis.toString() },
                    label = "Pilih ID Penulis",
                    onValueChangedEvent = { selectedId: String ->
                        onValueChange(updateBukuUiEvent.copy(idPenulis = selectedId))
                    }
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
                                selected = updateBukuUiEvent.statusBuku == option,
                                onClick = { onValueChange(updateBukuUiEvent.copy(statusBuku = option)) },
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
            }

            when (pnrbtUiState) {
                is HomePenerbitUiState.Loading -> {
                    // Menampilkan indikator loading
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }

                is HomePenerbitUiState.Error -> {
                    // Menampilkan pesan error
                    Text("Gagal mengambil data tanaman", color = MaterialTheme.colorScheme.error)
                }

                is HomePenerbitUiState.Success -> {
                    val penerbitList = pnrbtUiState.penerbit
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DynamicSelectedTextField(
                            selectedValue = updateBukuUiEvent.idPenerbit.toString(),
                            options = penerbitList.map { it.idPenerbit.toString() },
                            label = "Pilih ID Penerbit",
                            onValueChangedEvent = { selectedId: String ->
                                onValueChange(updateBukuUiEvent.copy(idPenerbit = selectedId))
                            }
                        )
                    }
                }
            }

            when (ktgrUiState) {
                is HomeKategoriUiState.Loading -> {
                    // Menampilkan indikator loading
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }

                is HomeKategoriUiState.Error -> {
                    // Menampilkan pesan error
                    Text("Gagal mengambil data tanaman", color = MaterialTheme.colorScheme.error)
                }

                is HomeKategoriUiState.Success -> {
                    val kategoriList = ktgrUiState.kategori
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DynamicSelectedTextField(
                            selectedValue = updateBukuUiEvent.idKategori,
                            options = kategoriList.map { it.idKategori.toString() },
                            label = "Pilih ID Kategori",
                            onValueChangedEvent = { selectedId: String ->
                                onValueChange(updateBukuUiEvent.copy(idKategori = selectedId))
                            }
                        )
                    }
                }
            }
        }
    }
}
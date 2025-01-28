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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectti.Navigation.DestinasiNavigasi
import com.example.projectti.ui.viewmodel.Penerbit.PenyediaPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penerbit.UpdatePenerbitUiEvent
import com.example.projectti.ui.viewmodel.Penerbit.UpdatePenerbitUiState
import com.example.projectti.ui.viewmodel.Penerbit.UpdatePenerbitViewModel
import com.example.projectti.ui.viewmodel.Penulis.PenyediaPenulisViewModel
import com.example.projectti.ui.viewmodel.Penulis.UpdatePenulisUiEvent
import com.example.projectti.ui.viewmodel.Penulis.UpdatePenulisUiState
import com.example.projectti.ui.viewmodel.Penulis.UpdatePenulisViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePenerbit : DestinasiNavigasi {
    override val route = "item_updatepenerbit"
    override val titleRes = "Update Penerbit"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateViewPenerbit(
    idPenerbit: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePenerbitViewModel = viewModel(factory = PenyediaPenerbitViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect (idPenerbit){
        viewModel.loadPenerbit(idPenerbit)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        UpdateBodyPenerbit(
            updatePenerbitUiState = viewModel.penerbituiState,
            onPenerbitValueChange = viewModel::updatePenerbitState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePenerbit(idPenerbit)
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
fun UpdateBodyPenerbit(
    updatePenerbitUiState: UpdatePenerbitUiState,
    onPenerbitValueChange: (UpdatePenerbitUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updatePenerbitUiEvent = updatePenerbitUiState.updatePenerbitUiEvent,
            onValueChange = onPenerbitValueChange,
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
    updatePenerbitUiEvent: UpdatePenerbitUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePenerbitUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updatePenerbitUiEvent.namaPenerbit,
            onValueChange = {onValueChange(updatePenerbitUiEvent.copy(namaPenerbit=it))},
            label = { Text("Nama Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updatePenerbitUiEvent.alamatPenerbit,
            onValueChange = {onValueChange(updatePenerbitUiEvent.copy(alamatPenerbit = it))},
            label = { Text("alamat Penerbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updatePenerbitUiEvent.teleponPenerbit,
            onValueChange = {onValueChange(updatePenerbitUiEvent.copy(teleponPenerbit =it))},
            label = { Text("telepon Penerbit") },
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
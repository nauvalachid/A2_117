package com.example.projectti.ui.view.Penerbit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectti.Navigation.DestinasiNavigasi
import com.example.projectti.model.Penerbit
import com.example.projectti.model.Penulis
import com.example.projectti.ui.viewmodel.Penerbit.DetailPenerbitUiState
import com.example.projectti.ui.viewmodel.Penerbit.DetailPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penerbit.PenyediaPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penulis.DetailPenulisUiState
import com.example.projectti.ui.viewmodel.Penulis.DetailPenulisViewModel
import com.example.projectti.ui.viewmodel.Penulis.PenyediaPenulisViewModel

object DestinasiDetailPenerbit : DestinasiNavigasi {
    override val route = "detailPenerbit"
    override val titleRes = "Detail Penerbit"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewPenerbit(
    idPenerbit: Int,
    navigateBack: ()-> Unit,
    onClick: () -> Unit,
    viewModel: DetailPenerbitViewModel = viewModel(factory = PenyediaPenerbitViewModel.Factory)
){
    val penerbituiState by viewModel.penerbituiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    viewModel.getPenerbitById(idPenerbit)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ){
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Penerbit")
            }
        },
    ) { innerPadding ->
        val penerbituiState = viewModel.penerbituiState.collectAsState().value
        DetailBodyPenerbit(
            penerbituiState = penerbituiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onDeleteClick = {
                viewModel.deletePnrbt(idPenerbit)
                navigateBack()
            }
        )
    }
}

@Composable
fun DetailBodyPenerbit(
    penerbituiState: DetailPenerbitUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    when (penerbituiState) {
        is DetailPenerbitUiState.Loading -> com.example.projectti.ui.view.Penerbit.OnLoading(modifier = modifier)
        is DetailPenerbitUiState.Success -> Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            ItemDetailPnrbt(penerbit = penerbituiState.penerbit)
            Button(
                onClick = { deleteConfirmationRequired = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Delete")
            }
        }
        is DetailPenerbitUiState.Error -> com.example.projectti.ui.view.Penerbit.OnError(
            retryAction = {},
            modifier = modifier
        )
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick()
            },
            onDeleteCancel = { deleteConfirmationRequired = false }
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}

@Composable
fun ItemDetailPnrbt(
    modifier: Modifier = Modifier,
    penerbit: Penerbit
){
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ){
        Column(modifier = Modifier.padding(16.dp)){
            ComponentDetailPnrbt(judul = "Id Penerbit", isinya = penerbit.idPenerbit.toString())
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPnrbt(judul = "Nama Penerbit", isinya = penerbit.namaPenerbit)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPnrbt(judul = "alamat Penerbit", isinya = penerbit.alamatPenerbit)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailPnrbt(judul = "telepon Penerbit", isinya = penerbit.teleponPenerbit)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ComponentDetailPnrbt(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = "$judul :",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )
        )
        Text(
            text = isinya,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp
            )
        )
    }
}
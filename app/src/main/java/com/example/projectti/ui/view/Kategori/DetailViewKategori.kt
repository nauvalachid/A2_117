package com.example.projectti.ui.view.Kategori

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
import com.example.projectti.model.Kategori
import com.example.projectti.model.Penerbit
import com.example.projectti.ui.viewmodel.Kategori.DetailKategoriUiState
import com.example.projectti.ui.viewmodel.Kategori.DetailKategoriViewModel
import com.example.projectti.ui.viewmodel.Kategori.PenyediaKategoriViewModel
import com.example.projectti.ui.viewmodel.Penerbit.DetailPenerbitUiState
import com.example.projectti.ui.viewmodel.Penerbit.DetailPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penerbit.PenyediaPenerbitViewModel

object DestinasiDetailKategori : DestinasiNavigasi {
    override val route = "detailKategori"
    override val titleRes = "Detail Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewKategori(
    idKategori: Int,
    navigateBack: ()-> Unit,
    onClick: () -> Unit,
    viewModel: DetailKategoriViewModel = viewModel(factory = PenyediaKategoriViewModel.Factory)
){
    val kategoriuiState by viewModel.katageoriuiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    viewModel.getKategoriById(idKategori)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ){
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Kategori")
            }
        },
    ) { innerPadding ->
        val kategoriuiState = viewModel.katageoriuiState.collectAsState().value
        DetailBodyKategori(
            kategoriuiState = kategoriuiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onDeleteClick = {
                viewModel.deleteKtgr(idKategori)
                navigateBack()
            }
        )
    }
}

@Composable
fun DetailBodyKategori(
    kategoriuiState: DetailKategoriUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    when (kategoriuiState) {
        is DetailKategoriUiState.Loading -> com.example.projectti.ui.view.Kategori.OnLoading(modifier = modifier)
        is DetailKategoriUiState.Success -> Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            ItemDetailKtgr(kategori = kategoriuiState.kategori)
            Button(
                onClick = { deleteConfirmationRequired = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Delete")
            }
        }
        is DetailKategoriUiState.Error -> com.example.projectti.ui.view.Kategori.OnError(
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
fun ItemDetailKtgr(
    modifier: Modifier = Modifier,
    kategori: Kategori
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
            ComponentDetailKtgr(judul = "Id Kategori", isinya = kategori.idKategori.toString())
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailKtgr(judul = "Nama Kategori", isinya = kategori.namaKategori)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailKtgr(judul = "Deskripsi Kategori ", isinya = kategori.deskripsiKategori)
        }
    }
}

@Composable
fun ComponentDetailKtgr(
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
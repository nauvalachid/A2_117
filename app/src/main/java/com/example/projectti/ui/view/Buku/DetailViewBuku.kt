package com.example.projectti.ui.view.Buku

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
import com.example.projectti.model.Buku
import com.example.projectti.model.Kategori
import com.example.projectti.ui.viewmodel.Buku.DetailBukuUiState
import com.example.projectti.ui.viewmodel.Buku.DetailBukuViewModel
import com.example.projectti.ui.viewmodel.Buku.PenyediaBukuViewModel
import com.example.projectti.ui.viewmodel.Kategori.DetailKategoriUiState
import com.example.projectti.ui.viewmodel.Kategori.DetailKategoriViewModel
import com.example.projectti.ui.viewmodel.Kategori.PenyediaKategoriViewModel

object DestinasiDetailBuku : DestinasiNavigasi {
    override val route = "detailBuku"
    override val titleRes = "Detail Kategori"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailViewBuku(
    idBuku: Int,
    navigateBack: ()-> Unit,
    onClick: () -> Unit,
    viewModel: DetailBukuViewModel = viewModel(factory = PenyediaBukuViewModel.Factory)
){
    val bukuuiState by viewModel.bukuuiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    viewModel.getBukuById(idBuku)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ){
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Buku")
            }
        },
    ) { innerPadding ->
        val bukuuiState = viewModel.bukuuiState.collectAsState().value
        DetailBodyBuku(
            bukuuiState = bukuuiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onDeleteClick = {
                viewModel.deleteBk(idBuku)
                navigateBack()
            }
        )
    }
}

@Composable
fun DetailBodyBuku(
    bukuuiState: DetailBukuUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }
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
fun ItemDetailBk(
    modifier: Modifier = Modifier,
    buku: Buku
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
            ComponentDetailBk(judul = "Id Buku", isinya = buku.idBuku.toString())
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBk(judul = "Nama Buku", isinya = buku.namaBuku)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBk(judul = "Deskripsi Buku ", isinya = buku.deskripsiBuku)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBk(judul = "Tanggal Terbit", isinya = buku.tanggalTerbit)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBk(judul = "Status Buku", isinya = buku.statusBuku)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBk(judul = "ID Kategori", isinya = buku.idKategori)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBk(judul = "ID Penulis", isinya = buku.idPenulis)
            Spacer(modifier = Modifier.height(8.dp))

            ComponentDetailBk(judul = "ID Penerbit", isinya = buku.idPenerbit)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ComponentDetailBk(
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
package com.example.projectti.ui.view.HalamanAwal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectti.Navigation.DestinasiNavigasi
import com.example.projectti.R
import com.example.projectti.model.Buku
import com.example.projectti.model.Kategori
import com.example.projectti.repository.BukuRepository
import com.example.projectti.ui.viewmodel.HalamanAwal.HomeHalamanAwalUiState
import com.example.projectti.ui.viewmodel.HalamanAwal.HomeHalamanAwalViewModel
import com.example.projectti.ui.viewmodel.HalamanAwal.PenyediaHalamanAwalViewModel

object DestinasiHalamanUtama : DestinasiNavigasi {
    override val route = "halamanutama"
    override val titleRes = "Halaman Utama"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHalamanAwalScreen(
    modifier: Modifier = Modifier,
    navigateToItemEntry : () -> Unit,
    onDetailClick: (Buku) -> Unit,
    onClickKategori: () -> Unit,
    onClickPenerbit: () -> Unit,
    onClickPenulis: () -> Unit,
    viewModel: HomeHalamanAwalViewModel = viewModel(factory = PenyediaHalamanAwalViewModel.Factory)
) {
    val hlmnUiState = viewModel.hlmnUiState

    LaunchedEffect(Unit) {
        viewModel.getHlmn()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Background gambar
        Image(
            painter = painterResource(id = R.drawable.ucppam), // Ganti dengan resource gambar Anda
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Menyesuaikan gambar agar memenuhi layar
        )
        Scaffold(
            modifier = modifier,
            containerColor = Color.Transparent,
            floatingActionButton = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    FloatingActionButton(
                        onClick = navigateToItemEntry,
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier
                            .padding(18.dp)
                            .offset(x = 15.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Buku")
                    }
                }
            }
    ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp), // Tambahkan jarak dari atas jika perlu
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.kmtii),
                        contentDescription = "Logo KMTII",
                        modifier = Modifier.size(250.dp) // Atur ukuran sesuai kebutuhan
                    )
                }
                DashboardStaticButtons(
                    onClickPenulis = onClickPenulis,
                    onClickKategori = onClickKategori,
                    onClickPenerbit = onClickPenerbit
                )
                Spacer(modifier = Modifier.height(16.dp)) // Spacer untuk jarak antar komponen
                HomeHalamanAwalStatus(
                    hlmnUiState = hlmnUiState,
                    onDetailClick = onDetailClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun DashboardStaticButtons(
    onClickKategori: () -> Unit,
    onClickPenerbit: () -> Unit,
    onClickPenulis: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tombol Kategori
        StaticButton(
            text = "Kategori",
            onClick = onClickKategori
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StaticButton(
                text = "Penerbit",
                onClick = onClickPenerbit
            )
            StaticButton(
                text = "Penulis",
                onClick = onClickPenulis
            )
        }
    }
}

@Composable
fun StaticButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.black), // Menggunakan warna dari tema
            contentColor = colorResource(R.color.blue)
        ),
        modifier = Modifier
            .padding(horizontal = 4.dp) // Jarak antar tombol
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun HomeHalamanAwalStatus(
    hlmnUiState: HomeHalamanAwalUiState,
    modifier: Modifier = Modifier,
    onDetailClick: (Buku) -> Unit
) {
    when (hlmnUiState) {
        is HomeHalamanAwalUiState.Loading -> {
            OnLoading(modifier = modifier)
        }
        is HomeHalamanAwalUiState.Success -> {
            if (hlmnUiState.buku.isEmpty()) {
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data buku")
                }
            } else {
                LazyColumn(
                    modifier = modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(hlmnUiState.buku) { buku ->
                        BukuCard(
                            buku = buku,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onDetailClick(buku) }
                        )
                    }
                }
            }
        }
        is HomeHalamanAwalUiState.Error -> {
            OnError(modifier = modifier)
        }
    }
}

@Composable
fun BukuCard(
    buku: Buku,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.darkblue))
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = buku.namaBuku,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = colorResource(R.color.grey2)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = buku.statusBuku,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(R.color.grey)
            )
        }
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun OnError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = "Error Image"
        )
        Text(
            text = stringResource(R.string.retry),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

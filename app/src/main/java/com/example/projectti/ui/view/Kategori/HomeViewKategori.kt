package com.example.projectti.ui.view.Kategori

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projectti.Navigation.DestinasiNavigasi
import com.example.projectti.R
import com.example.projectti.model.Buku
import com.example.projectti.model.Kategori
import com.example.projectti.ui.customwidget.CostumeTopAppBar
import com.example.projectti.ui.viewmodel.Kategori.HomeKategoriUiState
import com.example.projectti.ui.viewmodel.Kategori.HomeKategoriViewModel
import com.example.projectti.ui.viewmodel.Kategori.PenyediaKategoriViewModel

object DestinasiHomeKategori : DestinasiNavigasi {
    override val route = "homektgr"
    override val titleRes = "Home ktgr"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenKategori(
    navigateToItemEntryKategori:()-> Unit,
    modifier: Modifier = Modifier,
    onDetailClick:(Int)->Unit = {},
    viewModel: HomeKategoriViewModel = viewModel(factory= PenyediaKategoriViewModel.Factory)
){
    val scrollBehavior= TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier=modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeKategori.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKtgr()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryKategori,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kategori")
            }
        },
    ){innerPadding->
        HomeKategoriStatus(
            homeKategoriUiState=viewModel.ktgrUiState,
            retryAction = {viewModel.getKtgr()},modifier= Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deletektgr(it.idKategori)
                viewModel.getKtgr()
            }
        )
    }
}

@Composable
fun HomeKategoriStatus(
    homeKategoriUiState: HomeKategoriUiState,
    retryAction:()->Unit,
    modifier: Modifier = Modifier,
    onDeleteClick:(Kategori)->Unit={},
    onDetailClick: (Int) -> Unit
){
    when(homeKategoriUiState){
        is HomeKategoriUiState.Loading-> OnLoading(modifier=modifier.fillMaxSize())

        is HomeKategoriUiState.Success->
            if(homeKategoriUiState.kategori.isEmpty()){
                return Box(modifier=modifier.fillMaxSize(),contentAlignment= Alignment.Center){
                    Text(text="Tidak ada data Kategori")
                }
            }else{
                KtgrLayout(
                    kategori=homeKategoriUiState.kategori,modifier=modifier.fillMaxWidth(),
                    onDetailClick={
                        onDetailClick(it.idKategori)
                    },
                    onDeleteClick={
                        onDeleteClick(it)
                    }
                )
            }
        is HomeKategoriUiState.Error-> OnError(retryAction,modifier=modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier=modifier.size(25.dp),
        painter= painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun OnError(retryAction: () -> Unit,modifier: Modifier = Modifier){
    Column(
        modifier=modifier,
        verticalArrangement= Arrangement.Center,
        horizontalAlignment= Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id= R.drawable.error),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.loading),modifier= Modifier.padding(16.dp))
        Button(onClick=retryAction){
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun KtgrLayout(
    kategori: List<Kategori>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kategori) -> Unit,
    onDeleteClick: (Kategori) -> Unit = {}
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(kategori){kategori->
            KtgrCard(
                kategori = kategori,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(kategori) },
                onDeleteClick = {
                    onDeleteClick(kategori)
                }
            )
        }
    }
}

@Composable
fun KtgrCard(
    kategori: Kategori,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kategori) -> Unit={}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = kategori.namaKategori,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(kategori) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
            Text(
                text = kategori.deskripsiKategori,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
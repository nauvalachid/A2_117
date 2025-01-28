package com.example.projectti.ui.view.Penerbit

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
import com.example.projectti.model.Penerbit
import com.example.projectti.model.Penulis
import com.example.projectti.ui.customwidget.CostumeTopAppBar
import com.example.projectti.ui.viewmodel.Penerbit.HomePenerbitUiState
import com.example.projectti.ui.viewmodel.Penerbit.HomePenerbitViewModel
import com.example.projectti.ui.viewmodel.Penerbit.PenyediaPenerbitViewModel
import com.example.projectti.ui.viewmodel.Penulis.HomePenulisUiState
import com.example.projectti.ui.viewmodel.Penulis.HomePenulisViewModel
import com.example.projectti.ui.viewmodel.Penulis.PenyediaPenulisViewModel

object DestinasiHomePenerbit : DestinasiNavigasi {
    override val route = "homepenerbit"
    override val titleRes = "Home Pnrbt"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPenerbit(
    navigateToItemEntryPenerbit:()-> Unit,
    modifier: Modifier = Modifier,
    onDetailClick:(Int)->Unit = {},
    viewModel: HomePenerbitViewModel = viewModel(factory= PenyediaPenerbitViewModel.Factory)
){
    val scrollBehavior= TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier=modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePenerbit.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPnrbt()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntryPenerbit,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Penerbit")
            }
        },
    ){innerPadding->
        HomePenerbitStatus(
            homePenerbitUiState=viewModel.pnrbtUiState,
            retryAction = {viewModel.getPnrbt()},modifier= Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deletepnrbt(it.idPenerbit)
                viewModel.getPnrbt()
            }
        )
    }
}

@Composable
fun HomePenerbitStatus(
    homePenerbitUiState: HomePenerbitUiState,
    retryAction:()->Unit,
    modifier: Modifier = Modifier,
    onDeleteClick:(Penerbit)->Unit={},
    onDetailClick: (Int) -> Unit
){
    when(homePenerbitUiState){
        is HomePenerbitUiState.Loading-> OnLoading(modifier=modifier.fillMaxSize())

        is HomePenerbitUiState.Success->
            if(homePenerbitUiState.penerbit.isEmpty()){
                return Box(modifier=modifier.fillMaxSize(),contentAlignment= Alignment.Center){
                    Text(text="Tidak ada data Penerbit")
                }
            }else{
                PnrbtLayout(
                    penerbit=homePenerbitUiState.penerbit,modifier=modifier.fillMaxWidth(),
                    onDetailClick={
                        onDetailClick(it.idPenerbit)
                    },
                    onDeleteClick={
                        onDeleteClick(it)
                    }
                )
            }
        is HomePenerbitUiState.Error-> OnError(retryAction,modifier=modifier.fillMaxSize())
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
fun PnrbtLayout(
    penerbit: List<Penerbit>,
    modifier: Modifier = Modifier,
    onDetailClick: (Penerbit) -> Unit,
    onDeleteClick: (Penerbit) -> Unit = {}
){
    LazyColumn (
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(penerbit){penerbit->
            PnrbtCard(
                penerbit = penerbit,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(penerbit) },
                onDeleteClick = {
                    onDeleteClick(penerbit)
                }
            )
        }
    }
}

@Composable
fun PnrbtCard(
    penerbit: Penerbit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penerbit) -> Unit={}
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
                    text = penerbit.namaPenerbit,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(penerbit) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
            Text(
                text = penerbit.alamatPenerbit,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = penerbit.teleponPenerbit,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
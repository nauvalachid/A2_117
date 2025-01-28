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
import com.example.projectti.ui.viewmodel.Penulis.PenyediaPenulisViewModel
import com.example.projectti.ui.viewmodel.Penulis.UpdatePenulisUiEvent
import com.example.projectti.ui.viewmodel.Penulis.UpdatePenulisUiState
import com.example.projectti.ui.viewmodel.Penulis.UpdatePenulisViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePenulis : DestinasiNavigasi {
    override val route = "item_updatepenulis"
    override val titleRes = "Update Penulis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateViewPenulis(
    idPenulis: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePenulisViewModel = viewModel(factory = PenyediaPenulisViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect (idPenulis){
        viewModel.loadPenulis(idPenulis)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        UpdateBodyPenulis(
            updatePenulisUiState = viewModel.penulisuiState,
            onPenulisValueChange = viewModel::updatePenulisState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePenulis(idPenulis)
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
fun UpdateBodyPenulis(
    updatePenulisUiState: UpdatePenulisUiState,
    onPenulisValueChange: (UpdatePenulisUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updatePenulisUiEvent = updatePenulisUiState.updatePenulisUiEvent,
            onValueChange = onPenulisValueChange,
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
    updatePenulisUiEvent: UpdatePenulisUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePenulisUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updatePenulisUiEvent.namaPenulis,
            onValueChange = {onValueChange(updatePenulisUiEvent.copy(namaPenulis=it))},
            label = { Text("Nama Penulis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updatePenulisUiEvent.biografi,
            onValueChange = {onValueChange(updatePenulisUiEvent.copy(biografi = it))},
            label = { Text("Biografi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updatePenulisUiEvent.kontak,
            onValueChange = {onValueChange(updatePenulisUiEvent.copy(kontak=it))},
            label = { Text("Kontak") },
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

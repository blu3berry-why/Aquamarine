package hu.blueberry.projectaquamarine.features.stand.storage.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun StorageItemsListScreen(
    viewModel: StorageListViewModel = hiltViewModel(),
    worksheetName: String,
    navigateToProduct: (Int) -> Unit
) {

    LaunchedEffect(viewModel.productsAndStands) {
        viewModel.refresh(worksheetName)
    }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(worksheetName, fontSize = 20.sp, modifier = Modifier.padding(20.dp))
        HorizontalDivider()

        LazyColumn {
            items(viewModel.productsAndStands) {
                SingleItemStandRow(
                    productProperties = it.product!!,
                    stand = it.stand,
                    onClick = navigateToProduct
                )
            }
        }
    }
}
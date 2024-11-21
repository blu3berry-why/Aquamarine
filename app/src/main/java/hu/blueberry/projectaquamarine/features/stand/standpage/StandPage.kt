package hu.blueberry.projectaquamarine.features.stand.standpage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.blueberry.drinks.model.StandType
import hu.blueberry.projectaquamarine.navigation.stand.SingleStandItemScreen

@Composable
fun StandPage(
    onNavigateToSingleItemStandPage: (SingleStandItemScreen) -> Unit,
    viewModel: StandPageViewModel = hiltViewModel(),
){
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Button(onClick = { onNavigateToSingleItemStandPage(SingleStandItemScreen(1, viewModel.itemCount, StandType.OPEN.toStringValue())) }) {
            Text(text = "Open")
        }

        Button(onClick = { onNavigateToSingleItemStandPage(SingleStandItemScreen(1, viewModel.itemCount, StandType.CART.toStringValue())) }) {
            Text(text = "Cart")
        }

        Button(onClick = { onNavigateToSingleItemStandPage(SingleStandItemScreen(1, viewModel.itemCount, StandType.CLOSE.toStringValue())) }) {
            Text(text = "Close")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {  viewModel.saveProducts {  } }) {
            Text(text = "Save")
        }
    }

}
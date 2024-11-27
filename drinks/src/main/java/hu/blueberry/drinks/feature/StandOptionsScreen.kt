package hu.blueberry.drinks.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import hu.blueberry.drinks.viewModel.ProductDetailsViewModel

@Composable
fun StandOptionsScreen(
    navigateToProductList: () -> Unit,
    viewModel: ProductDetailsViewModel = hiltViewModel<ProductDetailsViewModel>()
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navigateToProductList() }) {
            Text(text = "Product List")
        }


        Button(onClick = { viewModel.readProductDetails("Segedlet") }) {
            Text(text = "Read SpreadSheet")
        }

        Button(onClick = { viewModel.readScaleInfo("Mérleg_segéd") }) {
            Text(text = "Read Scale")
        }

        Button(onClick = { viewModel.readStock() }) {
            Text(text = "Read F17")
        }

    }
}
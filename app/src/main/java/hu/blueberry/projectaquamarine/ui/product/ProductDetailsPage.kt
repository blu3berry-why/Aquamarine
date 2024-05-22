package hu.blueberry.projectaquamarine.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import hu.blueberry.projectaquamarine.viewModel.DetailsViewModel


@Preview
@Composable
fun ProductDetailsPagePreview(){
    ProductDetailsPage(0)
}
@Composable
fun ProductDetailsPage(
    id: Long,
    returnToPreviousPage: ()->Unit = {},
    viewModel: DetailsViewModel = hiltViewModel(),
){
    SideEffect {
        viewModel.getProduct(id)
    }

    val product = viewModel.product.collectAsState()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = product.value?.name ?: "")
        Text(text = product.value?.productType?.displayString ?: "")
        Text(text = product.value?.measureUnit?.displayString ?: "")
    }


}


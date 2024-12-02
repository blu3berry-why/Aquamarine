package hu.blueberry.projectaquamarine.features.product.old

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Preview
@Composable
fun ProductDetailsPagePreview(){
    ProductDetailsPage("")
}
@Composable
fun ProductDetailsPage(
    name: String,
    returnToPreviousPage: ()->Unit = {},
    viewModel: DetailsViewModel = hiltViewModel(),
){
    SideEffect {
        viewModel.getProduct(name)
    }

    val product = viewModel.product.collectAsState()
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = product.value?.name ?: "")
        Text(text = product.value?.productType?.displayString ?: "")
        Text(text = product.value?.measureUnit?.displayString ?: "")



        product.value?.storages?.let {
            for(storage in it){
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = storage.name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "open: " + storage.open)
                Text(text = "cart: " + storage.cart)
                Text(text = "close: " + storage.close)
            }
        }


    }


}


package hu.blueberry.projectaquamarine.features



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.blueberry.drinks.viewModel.ProductDetailsViewModel
import hu.blueberry.projectaquamarine.features.filepicker.FilePicker
import hu.blueberry.projectaquamarine.navigation.ProductList
import hu.blueberry.projectaquamarine.navigation.StoredPictures
import hu.blueberry.projectaquamarine.navigation.TakePhoto

@Composable
fun HomeMenuPage(
    navController: NavController,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Button(onClick = { navController.navigate(TakePhoto) }) {
            Text(text = "Take Photo")
        }

        Button(onClick = { navController.navigate(ProductList) }) {
            Text(text = "Product List")
        }

        Button(onClick = { navController.navigate(StoredPictures)}) {
            Text(text = "Stored Pictures")
        }

        Button(onClick = {viewModel.readProductDetails("Segedlet")}) {
            Text(text = "Read SpreadSheet")
        }


    }


}
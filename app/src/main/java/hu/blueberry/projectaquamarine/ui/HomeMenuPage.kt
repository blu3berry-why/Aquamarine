package hu.blueberry.projectaquamarine.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import hu.blueberry.projectaquamarine.navigation.ProductList
import hu.blueberry.projectaquamarine.navigation.TakePhoto
import hu.blueberry.projectaquamarine.navigation.stand.StandNavigation

@Composable
fun HomeMenuPage(
    navController: NavController,
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

    }
}
package hu.blueberry.projectaquamarine.features



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.blueberry.projectaquamarine.features.product.ProductDetailsViewModel

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



    }


}
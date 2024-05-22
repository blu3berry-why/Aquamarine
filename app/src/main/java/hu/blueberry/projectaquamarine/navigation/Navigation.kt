package hu.blueberry.projectaquamarine.navigation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import hu.blueberry.camera.ui.TakePhotoAndSetData
import hu.blueberry.projectaquamarine.ui.product.ProductListPage
import hu.blueberry.projectaquamarine.ui.AuthenticationPage
import hu.blueberry.projectaquamarine.ui.HomeMenuPage
import hu.blueberry.projectaquamarine.ui.product.ProductDetailsPage
import kotlinx.serialization.Serializable

@Composable
fun navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = AuthScreen) {
        composable<AuthScreen> {
            AuthenticationPage(navController = navController)
        }

        composable<ScreenB> {
            val args = it.toRoute<ScreenB>()
            Column (modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(text = "${args.name}, ${args.age} years old")
            }
        }
        
        composable<TakePhoto> {
            TakePhotoAndSetData()
        }

        composable<HomeMenuPage> {
            HomeMenuPage(navController = navController)
        }

        composable<ProductList> {
            ProductListPage(navController)
        }

        composable<ProductDetails> {
            val args = it.toRoute<ProductDetails>()
            ProductDetailsPage(args.id)
        }


    }
}

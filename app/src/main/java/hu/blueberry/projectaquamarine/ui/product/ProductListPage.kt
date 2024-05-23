package hu.blueberry.projectaquamarine.ui.product

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import hu.blueberry.drinks.viewModel.ProductsViewModel
import hu.blueberry.drive.permissions.ManagePermissionsWithPermissionManager
import hu.blueberry.projectaquamarine.navigation.ProductDetails
import hu.blueberry.projectaquamarine.navigation.stand.StandNavigation


@Composable
fun ProductListPage(
    navController: NavController,
    viewModel: ProductsViewModel = hiltViewModel(),
) {
    val list = viewModel.productList

    ManagePermissionsWithPermissionManager(permissionManager = viewModel.permissionManager)

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
            Button(onClick = { viewModel.readProducts() }) {
                Text(text = "Read")
            }
            Button(onClick = { viewModel.saveProducts {  }

            }) {
                Text(text = "Save")
            }
            Button(onClick = { navController.navigate(StandNavigation) }) {
                Text(text = "Stand")
            }
        }

        LazyColumn {
            items(list.size) { index: Int ->
                val product = list[index]
                ProductRow(product = product) {
                    navController.navigate(
                        ProductDetails(
                            product.name!!
                        )
                    )
                }
            }
        }


    }

}
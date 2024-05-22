package hu.blueberry.projectaquamarine.ui.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.blueberry.drinks.viewModel.ProductsViewModel
import hu.blueberry.drive.permissions.ManagePermissionsWithPermissionManager
import hu.blueberry.projectaquamarine.navigation.ProductDetails


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

        Button(onClick = { viewModel.addProduct() }) {
            Text(text = "Add Product")
        }
        LazyColumn {
            items(list.size) { index: Int ->
                val product = list[index]
                ProductRow(product = product) {
                    navController.navigate(
                        ProductDetails(
                            product.id ?: 1
                        )
                    )
                }
            }
        }


    }

}
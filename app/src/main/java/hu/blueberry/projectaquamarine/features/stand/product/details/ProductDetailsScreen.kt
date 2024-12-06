package hu.blueberry.projectaquamarine.features.stand.product.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    productId: Int,
) {

    LaunchedEffect(viewModel.productAndScaleInfo) {
        viewModel.getProduct(productId)
    }

    val productAndScaleInfo = viewModel.productAndScaleInfo.collectAsState()

    val product = productAndScaleInfo.value?.productProperties
    val scale = productAndScaleInfo.value?.scaleInfo

    if (product != null && scale != null) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier.fillMaxSize(),
        ) {
            Text(
                text = product.name
            )

            Row {
                ElevatedCardForProperty(
                    name = "Type",
                    value = product.type.displayString,
                )

                ElevatedCardForProperty(
                    name = "Mesure Unit",
                    value = product.measureUnit.displayString
                )
            }

            Row {
                ElevatedCardForProperty(
                    name = "Selling Price",
                    value = product.sellingPrice.toString()
                )
                ElevatedCardForProperty(
                    name = "Selling quantity for single product",
                    value = product.sellingQuantityForPurchasedSingleProduct.toString()
                )

            }

            Row {
                ElevatedCardForProperty(
                    name = "Full Weight",
                    value = scale.fullWeight.toString()
                )
                ElevatedCardForProperty(
                    name = "Bottle Weight",
                    value = scale.emptyWeight.toString()
                )
            }

            ElevatedCardForProperty(
                name = "Container capacity",
                value = scale.containerCapacity.toString() + " " + product.measureUnit.displayString
            )

        }
    }
}

@Composable
fun ElevatedCardForProperty(
    name: String,
    value: String,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .size(width = 200.dp, height = 100.dp)
            .padding(4.dp),

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = name,
                modifier = Modifier,
                textAlign = TextAlign.Center,
            )
            Text(
                text = value,
                modifier = Modifier,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun ProductDetailsScreenPreview() {
    ProductDetailsScreen(
        productId = 4
    )
}
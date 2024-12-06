package hu.blueberry.projectaquamarine.features.stand.product.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.blueberry.persistentstorage.model.MeasureUnit
import hu.blueberry.persistentstorage.model.ProductType
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductProperties


@Composable
fun ProductPropertiesCard(
    productProperties: ProductProperties,
    modifier: Modifier = Modifier,
    navigateToProduct: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .clickable {
                navigateToProduct(productProperties.id!!)
            }
            .fillMaxWidth()
            .height(62.dp)
            .padding(top = 6.dp)
    ) {

        Icon(
            imageVector = productProperties.type.toImageVector(),
            contentDescription = "Beer",
            modifier = Modifier.padding(start = 10.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(start = 6.dp, top = 2.dp)
        ) {
            Text(text = productProperties.name, fontWeight = FontWeight(500), fontSize = 18.sp)
            Text(text = productProperties.sellingPrice.toString() + " Ft")
        }


    }
    HorizontalDivider()
}


@Preview
@Composable
fun ProductPropertiesCardPreview() {
    Column {
        ProductPropertiesCard(
            productProperties = ProductProperties(
                id = null,
                name = "Edelweiss Hefe (0,5L)",
                type = ProductType.BEER,
                measureUnit = MeasureUnit.LITER,
                sellingPrice = 1000,
                sellingQuantityForPurchasedSingleProduct = 50.0,
            ),
            navigateToProduct = { },
        )
        ProductPropertiesCard(
            productProperties = ProductProperties(
                id = null,
                name = "Edelweiss Hefe (0,5L)",
                type = ProductType.BEER,
                measureUnit = MeasureUnit.LITER,
                sellingPrice = 1000,
                sellingQuantityForPurchasedSingleProduct = 50.0,
            ),
            navigateToProduct = { },
        )

    }

}
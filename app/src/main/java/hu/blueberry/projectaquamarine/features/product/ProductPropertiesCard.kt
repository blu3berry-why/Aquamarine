package hu.blueberry.projectaquamarine.features.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Liquor
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SportsBar
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WineBar
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
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .height(62.dp)
            .padding(top = 6.dp)
    ) {

        Icon(
            imageVector = when(productProperties.type){
                ProductType.BEER -> Icons.Default.SportsBar
                ProductType.WINE -> Icons.Default.WineBar
                ProductType.ENERGY_DRINK -> Icons.Default.Bolt
                ProductType.SPIRIT -> Icons.Default.Liquor
                ProductType.FOOD -> Icons.Default.LocalPizza
                ProductType.SOFT_DRINK -> Icons.Default.LocalDrink
                ProductType.COCKTAIL_INGREDIENT -> Icons.Default.LocalBar
                ProductType.PREMIUM_SPIRIT -> Icons.Default.Sell
                ProductType.PALINKA -> Icons.Default.WaterDrop
                ProductType.OTHER -> Icons.Default.ShoppingBag
            }

            ,
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
}

@Preview
@Composable
fun ProductPropertiesCardPreview(){
    Column {
        ProductPropertiesCard(
            productProperties = ProductProperties(
                id = null,
                name = "Edelweiss Hefe (0,5L)",
                type = ProductType.BEER,
                measureUnit = MeasureUnit.LITER,
                sellingPrice = 1000,
                sellingQuantityForPurchasedSingleProduct = 50.0,
            )
        )
        ProductPropertiesCard(
                productProperties = ProductProperties(
                    id = null,
                    name = "Edelweiss Hefe (0,5L)",
                    type = ProductType.BEER,
                    measureUnit = MeasureUnit.LITER,
                    sellingPrice = 1000,
                    sellingQuantityForPurchasedSingleProduct = 50.0,
                )
                )
    }

}
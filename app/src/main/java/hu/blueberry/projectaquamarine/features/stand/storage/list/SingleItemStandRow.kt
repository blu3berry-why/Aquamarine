package hu.blueberry.projectaquamarine.features.stand.storage.list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.blueberry.drive.model.StandType
import hu.blueberry.drive.model.StandType.CART
import hu.blueberry.drive.model.StandType.CLOSE
import hu.blueberry.drive.model.StandType.OPEN
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductProperties
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductStand

@Composable
fun SingleItemStandRow(
    modifier: Modifier = Modifier,
    productProperties: ProductProperties,
    stand: ProductStand,
    onClick: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .clickable {
                onClick(productProperties.id!!)
            }
            .fillMaxWidth()
            .height(70.dp)
            .padding(top = 6.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = productProperties.type.toImageVector(),
                    contentDescription = "Beer",
                    modifier = Modifier.padding(start = 10.dp)
                )

                Text(text = productProperties.name, fontWeight = FontWeight(500), fontSize = 18.sp)
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(start = 6.dp, top = 2.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth(0.8f)
                ) {
                    StandAssistChip(
                        value = stand.openingStock.toString(),
                        standType = StandType.OPEN
                    )

                    StandAssistChip(
                        value = stand.stockChange.toString(),
                        standType = StandType.CART
                    )

                    StandAssistChip(
                        value = stand.closingStock.toString(),
                        standType = StandType.CLOSE
                    )
                }
            }
        }
    }
    HorizontalDivider()
}

@Composable
fun StandAssistChip(
    value: String,
    standType: StandType
) {
    AssistChip(
        onClick = { Log.d("Assist chip", "StandType: ${standType.name} value: $value") },
        label = { Text(value) },
        leadingIcon = {
            Icon(
                imageVector = standType.toImageVector(),
                contentDescription = "Localized description",
                modifier = Modifier.size(AssistChipDefaults.IconSize),
            )
        }
    )
}

fun StandType.toImageVector(): ImageVector {
    return when (this) {
        OPEN -> Icons.Default.Start
        CART -> Icons.Default.ShoppingCart
        CLOSE -> Icons.Default.Key
    }
}
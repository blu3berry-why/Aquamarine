package hu.blueberry.persistentstorage.model

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
import androidx.compose.ui.graphics.vector.ImageVector

enum class ProductType(val displayString: String) {
    BEER("SÖR"),
    WINE("BOROK"),
    ENERGY_DRINK("ENERGIA ITAL"),
    SPIRIT("TÖMÉNY"),
    FOOD("HARAPNIVALÓK"),
    SOFT_DRINK("ÜDÍTŐK"),
    COCKTAIL_INGREDIENT("KOKTÉL"),
    PREMIUM_SPIRIT("PRÉMIUM TÖMÉNY"),
    PALINKA("PÁLINKÁK"),
    OTHER("KIFUTÓ, EGYÉB"),
    ;

    companion object {
        fun isProductType(string: String): Boolean {
            return ProductType.entries
                .map { it.displayString.lowercase() }
                .contains(string.lowercase())
        }

        fun fromString(string: String): ProductType {
            return when (string) {
                BEER.displayString -> BEER
                WINE.displayString -> WINE
                ENERGY_DRINK.displayString -> ENERGY_DRINK
                SPIRIT.displayString -> SPIRIT
                FOOD.displayString -> FOOD
                SOFT_DRINK.displayString -> SOFT_DRINK
                COCKTAIL_INGREDIENT.displayString -> COCKTAIL_INGREDIENT
                PREMIUM_SPIRIT.displayString -> PREMIUM_SPIRIT
                PALINKA.displayString -> PALINKA
                OTHER.displayString -> OTHER
                else -> {
                    throw Exception(
                        "There is no type associated with '$string' product type. The types are: " +
                                ProductType.entries.joinToString { "${it.displayString}, " }
                    )
                }
            }
        }
    }
        fun toImageVector(): ImageVector {
            return when(this){
                BEER -> Icons.Default.SportsBar
                WINE -> Icons.Default.WineBar
                ENERGY_DRINK -> Icons.Default.Bolt
                SPIRIT -> Icons.Default.Liquor
                FOOD -> Icons.Default.LocalPizza
                SOFT_DRINK -> Icons.Default.LocalDrink
                COCKTAIL_INGREDIENT -> Icons.Default.LocalBar
                PREMIUM_SPIRIT -> Icons.Default.Sell
                PALINKA -> Icons.Default.WaterDrop
                OTHER -> Icons.Default.ShoppingBag
            }
        }

}
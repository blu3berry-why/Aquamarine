package hu.blueberry.persistentstorage.model

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
}
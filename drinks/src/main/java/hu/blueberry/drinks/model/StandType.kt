package hu.blueberry.drinks.model

import kotlinx.serialization.Serializable

@Serializable
enum class StandType() {
    OPEN(), CART(), CLOSE();
    fun toStringValue() = this.name

    fun fromStringValue(string: String) = enumValueOf<StandType>(string)
}
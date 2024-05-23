package hu.blueberry.drinks.model

import kotlinx.serialization.Serializable

@Serializable
enum class StandType() {
    OPEN(), CART(), CLOSE();

    companion object{
        fun fromStringValue(string: String) = enumValueOf<StandType>(string)
    }
    fun toStringValue() = this.name
}
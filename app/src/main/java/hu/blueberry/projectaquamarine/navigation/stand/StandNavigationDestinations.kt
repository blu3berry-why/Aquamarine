package hu.blueberry.projectaquamarine.navigation.stand

import androidx.navigation.NavType
import hu.blueberry.drinks.model.StandType
import kotlinx.serialization.Serializable

@Serializable
data class SingleStandItemScreen(val id: Long, val itemCount: Int, val standType: String)

@Serializable
object StandScreen

@Serializable
object StandNavigation
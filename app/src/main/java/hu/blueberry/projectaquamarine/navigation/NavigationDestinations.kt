package hu.blueberry.projectaquamarine.navigation

import kotlinx.serialization.Serializable


@Serializable
object TakePhoto

@Serializable
object AuthScreen

@Serializable
data class ScreenB(
    val name: String?,
    val age: Int
)

@Serializable
object HomeMenuPage

@Serializable
object ProductList

@Serializable
data class ProductDetails(val id:Long)
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
data class ProductDetails(val name:String)

@Serializable
object StoredPictures

@Serializable
object MenuScreen

@Serializable
object SelectFolder

@Serializable
class SelectFiles(
    val fileTypes: List<String>,
    val chooseType: String
)

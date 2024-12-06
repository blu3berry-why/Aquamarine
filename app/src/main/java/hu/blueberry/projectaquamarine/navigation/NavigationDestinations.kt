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
data class ProductDetails(val id:Int)

@Serializable
object StoredPictures

@Serializable
object MenuScreen

@Serializable
object SelectFolder

@Serializable
class SelectFiles(
    val fileTypes: List<String>,
    val chooseType: String,
)

@Serializable
object ProductListScreen

@Serializable
data class StorageItemList(
    val storageName: String,
)

@Serializable
data class StorageItemDetails(
    val productId: Int,
)







package hu.blueberry.projectaquamarine.features.stand.product.details

import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.repository.ProductPropertiesRepository
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.persistentstorage.model.updatedextradata.merged.ProductAndScaleInfo
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productPropertiesRepository: ProductPropertiesRepository
) : PermissionHandlingViewModel(){

    val productAndScaleInfo: MutableStateFlow<ProductAndScaleInfo?> = MutableStateFlow(null)

    fun getProduct(productId:Int){
        runIO(
            request = {
                productAndScaleInfo.value = productPropertiesRepository.getProductAndScaleInfo(productId)
            }
        )
    }
}
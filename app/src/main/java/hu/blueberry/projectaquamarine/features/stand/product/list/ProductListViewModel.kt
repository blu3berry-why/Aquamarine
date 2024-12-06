package hu.blueberry.projectaquamarine.features.stand.product.list

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.model.MemoryDatabase2
import hu.blueberry.drinks.repository.ProductPropertiesRepository
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductProperties
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productPropertiesRepository: ProductPropertiesRepository,
    private val memoryDatabase2: MemoryDatabase2
) : PermissionHandlingViewModel() {



    val productList: SnapshotStateList<ProductProperties> = mutableStateListOf()

    init {
        loadProducts()
    }

    private fun loadProducts(){
        runIO(
            request = {
                productList.clear()
                productList.addAll(productPropertiesRepository.getAllProducts(
                    memoryDatabase2.workingDirectory.choosenSpreadSheet!!.id))
            }
        )
    }
}
package hu.blueberry.projectaquamarine.features.stand.storage.details

import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.model.MemoryDatabase2
import hu.blueberry.drinks.repository.ProductPropertiesRepository
import hu.blueberry.drinks.repository.StandRepository
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductProperties
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductStand
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class SingleItemStandDetailsViewModel @Inject constructor(
    private val standRepository: StandRepository,
    private val productPropertiesRepository: ProductPropertiesRepository,
    private val memoryDatabase2: MemoryDatabase2
) : PermissionHandlingViewModel() {

    val productStand: MutableStateFlow<ProductStand?> = MutableStateFlow(null)

    val productProperties: MutableStateFlow<ProductProperties?> = MutableStateFlow(null)

    fun loadProduct(id: Int) {
        memoryDatabase2.runCodeIfSpreadsheetIdNotNull { spreadsheetId ->
            memoryDatabase2.runCodeIfWorksheetNameNotNull { worksheetName ->
                runIO {
                    productStand.value = standRepository.getProductStand(
                        spreadsheetId = spreadsheetId,
                        worksheetName = worksheetName,
                        id = id
                    )
                    productProperties.value = productPropertiesRepository.getProductProperties(id)
                }
            }

        }
    }

    fun updateProduct(
        openingStock: Double? = null,
        stockChange: Double? = null,
        closingStock: Double? = null,
    ){
        openingStock?.let { productStand.value = productStand.value?.copy(openingStock = openingStock) }
        stockChange?.let { productStand.value = productStand.value?.copy(stockChange = stockChange) }
        closingStock?.let { productStand.value = productStand.value?.copy(closingStock = closingStock) }
    }


    fun saveProduct(){
        memoryDatabase2.runCodeIfSpreadsheetIdNotNull { spreadsheetId ->
            memoryDatabase2.runCodeIfWorksheetNameNotNull { worksheetName ->
                runIO {
                    productStand.value?.let {  productStand ->
                        standRepository.writeProductIntoSpreadsheet(spreadsheetId, worksheetName, productStand)
                    }

                }
            }

        }
    }
}


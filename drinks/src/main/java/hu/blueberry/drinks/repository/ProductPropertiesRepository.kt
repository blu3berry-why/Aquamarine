package hu.blueberry.drinks.repository

import hu.blueberry.drive.base.ResourceState
import hu.blueberry.drive.base.handleWithFlow
import hu.blueberry.drive.repositories.GoogleSheetRepository
import hu.blueberry.drive.services.GoogleSheetsService
import hu.blueberry.persistentstorage.model.MeasureUnit
import hu.blueberry.persistentstorage.model.ProductType
import hu.blueberry.persistentstorage.model.updatedextradata.ProductProperties
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductPropertiesRepository @Inject constructor(
    private val googleSheetsService: GoogleSheetsService
) {

    suspend fun readProductDetailsFlow(spreadSheetId: String, workSheetName: String): Flow<ResourceState<MutableList<ProductProperties>>> {
        return handleWithFlow { readProductDetails(spreadSheetId, workSheetName) }
    }

    private suspend fun readProductDetails(spreadSheetId: String, workSheetName: String): MutableList<ProductProperties> {
        val range = "$workSheetName!A2:G"
        // TODO handle error!
        val valueRange = googleSheetsService.readSpreadSheetFormula(spreadSheetId, range) ?: TODO()

        var productType = ProductType.BEER

        val productList: MutableList<ProductProperties> = mutableListOf()

        for (row in valueRange.getValues()) {
            if (row.size > 6) {
                val product = createProductFromListWithIndexes(row, productType = productType)
                productList.add(product)
            }else{
                productType = ProductType.fromString(row[0].toString())
            }
        }
        return productList

    }

    private fun createProductFromListWithIndexes(
        list: List<Any>,
        productType: ProductType,
        nameIndex: Int = 0,
        measureUnitIndex: Int = 1,
        sellingPriceIndex: Int = 3,
        sellingQuantityIndex: Int = 6
    ): ProductProperties {
        return ProductProperties(
            name = list[nameIndex].toString(),
            type = productType,
            measureUnit = MeasureUnit.fromString(list[measureUnitIndex].toString()),
            sellingPrice = list[sellingPriceIndex].toString().toInt(),
            sellingQuantityForPurchasedSingleProduct = list[sellingQuantityIndex].toString()
                .toDouble()

        )
    }

}
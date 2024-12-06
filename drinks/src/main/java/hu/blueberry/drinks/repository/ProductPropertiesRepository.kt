package hu.blueberry.drinks.repository

import hu.blueberry.drive.base.ResourceState
import hu.blueberry.drive.base.handleWithFlow
import hu.blueberry.drive.services.GoogleSheetsService
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.model.MeasureUnit
import hu.blueberry.persistentstorage.model.ProductType
import hu.blueberry.persistentstorage.model.updatedextradata.merged.ProductAndScaleInfo
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductProperties
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductSpreadSheetInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductPropertiesRepository @Inject constructor(
    private val googleSheetsService: GoogleSheetsService,
    private val database: Database,
) {

    suspend fun readProductDetailsFlow(
        spreadSheetId: String,
        workSheetName: String
    ): Flow<ResourceState<MutableList<ProductProperties>>> {
        return handleWithFlow { readProductProperties(spreadSheetId, workSheetName) }
    }

    suspend fun readProductProperties(
        spreadSheetId: String,
        workSheetName: String,
        startRow: Int = 2
    ): MutableList<ProductProperties> {
        val range = "$workSheetName!A$startRow:G"
        // TODO handle error!
        val valueRange = googleSheetsService.readSpreadSheetFormula(spreadSheetId, range) ?: TODO()

        var productType = ProductType.BEER

        val productAndRowList: MutableList<Pair<ProductProperties, Int>> = mutableListOf()

        var actualRow = startRow

        for (row in valueRange.getValues()) {
            if (row.size > 6) {
                val product = createProductFromListWithIndexes(row, productType = productType)
                productAndRowList.add(product to actualRow)
            } else {
                productType = ProductType.fromString(row[0].toString())
            }
            actualRow += 1
        }

        //Update Cache
        updateCachedProductPropertiesAndInSpreadSheetRow(productAndRowList, spreadSheetId)

        return productAndRowList.map { it.first }.toMutableList()
    }

    private suspend fun updateCachedProductPropertiesAndInSpreadSheetRow(newList: List<Pair<ProductProperties, Int>>,
                                                                         spreadSheetId: String) {
        val productPropertiesList = newList.map { it.first }

        upsertProductProperties(
            databaseList = database.productNewDao().getAllProductProperties(),
            productPropertiesList
        )

        updateCachedRowInSpreadSheet(newList, spreadSheetId)
    }


    private suspend fun updateCachedRowInSpreadSheet(
        newList: List<Pair<ProductProperties, Int>>,
        spreadSheetId: String
    ) {

        val cachedRowInSpreadSheetInfos = database.productSpreadSheetInfoDao()
            .getAllProductSpreadSheetInfo(spreadSheetId)

        for (productAndRow in newList) {
            val productId = database.productNewDao().getProduct(productAndRow.first.name).id
            val spreadSheetInfo =
                cachedRowInSpreadSheetInfos.firstOrNull { it.productPropertiesId == productId }

            if (spreadSheetInfo == null) {
                database
                    .productSpreadSheetInfoDao()
                    .upsertProductSpreadSheetInfo(
                        ProductSpreadSheetInfo(
                            id = null,
                            productPropertiesId = productId!!,
                            rowInSpreadSheet = productAndRow.second,
                            spreadSheetId = spreadSheetId
                        )
                    )
            }else{
                database.
                productSpreadSheetInfoDao().
                upsertProductSpreadSheetInfo(
                    spreadSheetInfo.copy(
                        rowInSpreadSheet = productAndRow.second
                    )
                )
            }
        }
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

    private suspend fun upsertProductProperties(
        databaseList: List<ProductProperties>,
        newList: List<ProductProperties>
    ) {
        val listToSave: MutableList<ProductProperties> = mutableListOf()

        for (product in newList) {
            val savedProduct = databaseList.firstOrNull { it.name == product.name }
            if (savedProduct == null) {
                listToSave.add(product)
            } else {
                listToSave.add(product.copy(id = savedProduct.id!!))
            }
        }

        database.productNewDao().upsertProductPropertyList(listToSave)
    }

    suspend fun getAllProducts(spreadsheetId: String):List<ProductProperties>{
        return database.productFittingDao().getProductProperties(spreadsheetId)
    }

    suspend fun getProductAndScaleInfo(productId:Int): ProductAndScaleInfo {
        return database.productNewDao().getProductAndScaleInfo(productId) ?: throw IllegalArgumentException("Product with id '$productId' does not exist!")
    }

    suspend fun getProductProperties(id:Int): ProductProperties {
        return database.productNewDao().getProduct(id)
    }
}
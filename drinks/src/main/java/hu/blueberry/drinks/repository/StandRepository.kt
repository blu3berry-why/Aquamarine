package hu.blueberry.drinks.repository

import hu.blueberry.drive.base.ResourceState
import hu.blueberry.drive.base.handleWithFlow
import hu.blueberry.drive.services.GoogleSheetsService
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.dao.ProductIdAndRowInSpreadSheet
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductStand
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StandRepository @Inject constructor(
    private val googleSheetsService: GoogleSheetsService,
    private val database: Database,
    private val templateFunctionsRepository: TemplateFunctionsRepository
) {

    var worksheetId: Int? = null

    suspend fun getStorageSheetNames(spreadsheetId: String, storageMarker: String): List<String> {
        val worksheetNames = googleSheetsService.listWorkSheetNames(spreadsheetId)
        return worksheetNames.filter { worksheetName -> worksheetName.contains(storageMarker) }
    }

    suspend fun readStorageSheetFlow(spreadsheetId: String, worksheetName: String): Flow<ResourceState<Unit>> {
        return handleWithFlow { readStorageSheet(spreadsheetId, worksheetName) }
    }

    private suspend fun readStorageSheet(spreadsheetId: String, worksheetName: String) {
        //get cached info
        val worksheetAndProductStands = database.standDao()
            .getWorksheetAndProductStandsForSpreadsheet(spreadsheetId)
            .firstOrNull { it.worksheetStorageInfo.worksheetName == worksheetName }

        if (worksheetAndProductStands == null) {
            throw IllegalArgumentException(
                "Worksheet with the name: \"$worksheetName\" does not exists!"
            )
        }

        worksheetId = worksheetAndProductStands.worksheetStorageInfo.id

        templateFunctionsRepository.readItems(
            spreadSheetId = spreadsheetId,
            worksheetName = worksheetName,
            startColumn = "C",
            endColumn = "E",
            parseItem = ::parseProductStand,
            updateCacheItem = ::updateCacheProdutStand
        )
    }

    fun parseProductStand(
        row: MutableList<Any>,
        productIdAndRowInSpreadSheet: ProductIdAndRowInSpreadSheet
    ): ProductStand {
        return ProductStand(
            id = null,
            productOwnerId = productIdAndRowInSpreadSheet.rowInSpreadSheet,
            worksheetId = worksheetId!!,
            openingStock = templateFunctionsRepository.stringToDoubleHandle(row.getOrNull(0))
                ?: 0.0,
            stockChange = templateFunctionsRepository.stringToDoubleHandle(row.getOrNull(1)) ?: 0.0,
            closingStock = templateFunctionsRepository.stringToDoubleHandle(row.getOrNull(2))
                ?: 0.0,
        )
    }

    fun updateCacheProdutStand(productStand: ProductStand) {
        templateFunctionsRepository.updateCachedItemOrInsertIfNotExists(
            newItem = productStand,
            getItemFromDatabase = {
                database
                    .standDao()
                    .getProductStandByWorksheetIdAndProductId(
                        worksheetId = it.worksheetId,
                        productId = it.productOwnerId
                    )
            },
            upsertNotYetCachedItem = {
                database.standDao()
                    .upsertProductStand(
                        productStand = it
                    )
            },
            upsertUpdatedCachedItem = { cached, new ->
                database.standDao()
                    .upsertProductStand(
                        cached.copy(
                            openingStock = new.openingStock,
                            stockChange = new.stockChange,
                            closingStock = new.closingStock
                        )
                    )
            }
        )

    }


}
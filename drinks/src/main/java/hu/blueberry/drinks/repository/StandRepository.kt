package hu.blueberry.drinks.repository

import hu.blueberry.drive.base.ResourceState
import hu.blueberry.drive.base.handleWithFlow
import hu.blueberry.drive.model.Settings
import hu.blueberry.drive.model.google.RangeBuilder
import hu.blueberry.drive.services.GoogleSheetsService
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.dao.ProductAndStand
import hu.blueberry.persistentstorage.dao.ProductIdAndRowInSpreadSheet
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductStand
import hu.blueberry.persistentstorage.model.updatedextradata.spreadsheet.ProductStandDataWritableToSpreadsheet
import hu.blueberry.persistentstorage.model.updatedextradata.spreadsheet.WorksheetStorageInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StandRepository @Inject constructor(
    private val googleSheetsService: GoogleSheetsService,
    private val database: Database,
    private val scaleRepository: ScaleRepository,
    private val templateFunctionsRepository: TemplateFunctionsRepository,
    private val propertiesRepository: ProductPropertiesRepository
) {

    var worksheetId: Int? = null

    suspend fun readStorageSheetFlow(
        spreadsheetId: String,
        worksheetName: String
    ): Flow<ResourceState<Unit>> {
        return handleWithFlow { readStorageSheet(spreadsheetId, worksheetName) }
    }

    suspend fun readAllStorageSheetsFlow(
        spreadsheetId: String,
        storageMarker: String
    ): Flow<ResourceState<Unit>> {
        return handleWithFlow { readAllStorageSheets(spreadsheetId, storageMarker) }
    }

    private suspend fun getStorageSheetNames(
        spreadsheetId: String,
        storageMarker: String
    ): List<String> {
        val worksheetNames = googleSheetsService.listWorkSheetNames(spreadsheetId)
        return worksheetNames.filter { worksheetName -> worksheetName.contains(storageMarker) }
    }

    private suspend fun updateCachedStorageNames(spreadsheetId: String, storageMarker: String) {
        val storageNames = getStorageSheetNames(spreadsheetId, storageMarker)

        val cachedWorksheetStorageInfos =
            database.standDao().getWorksheetAndProductStands(spreadsheetId)

        for (storageName in storageNames) {
            val worksheetStorageInfo =
                cachedWorksheetStorageInfos.firstOrNull { it.worksheetStorageInfo.worksheetName == storageName }
            if (worksheetStorageInfo == null) {
                database.standDao().upsertWorksheetStorageInfo(
                    WorksheetStorageInfo(
                        id = null,
                        spreadSheetId = spreadsheetId,
                        worksheetName = storageName
                    )
                )
            }
        }
    }


    suspend fun readAllStorageSheets(spreadsheetId: String, storageMarker: String) {
        //TODO
        propertiesRepository.readProductProperties(spreadsheetId, Settings.ProductPropertiesWorksheet)
        scaleRepository.readScale(spreadsheetId, Settings.ScaleSheetName)

        updateCachedStorageNames(spreadsheetId, storageMarker)

    }

    suspend fun readStorageSheet(spreadsheetId: String, worksheetName: String) {
        updateCachedStorageNames(spreadsheetId, storageMarker = Settings.StorageMarker)

        //get cached info
        val worksheetAndProductStands = database.standDao()
            .getWorksheetAndProductStands(spreadsheetId, worksheetName)

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
            updateCacheItem = ::updateCacheProductStand
        )
    }

    fun parseProductStand(
        row: MutableList<Any>,
        productIdAndRowInSpreadSheet: ProductIdAndRowInSpreadSheet
    ): ProductStand {
        return ProductStand(
            id = null,
            productOwnerId = productIdAndRowInSpreadSheet.productId,
            worksheetId = worksheetId!!,
            openingStock = templateFunctionsRepository.stringToDoubleHandle(row.getOrNull(0))
                ?: 0.0,
            stockChange = templateFunctionsRepository.stringToDoubleHandle(row.getOrNull(1)) ?: 0.0,
            closingStock = templateFunctionsRepository.stringToDoubleHandle(row.getOrNull(2))
                ?: 0.0,
        )
    }

    fun updateCacheProductStand(productStand: ProductStand) {
        templateFunctionsRepository.updateCachedItemOrInsertIfNotExists(
            newItem = productStand,
            getItemFromDatabase = {
                database
                    .standDao()
                    .getProductStand(
                        worksheetId = it.worksheetId,
                        productId = it.productOwnerId
                    )
            },
            insertNotYetCachedItem = {
                database.standDao()
                    .upsertProductStand(
                        productStand = it
                    )
            },
            updateUpdatedCachedItem = { cached, new ->
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

    suspend fun getWorksheetInfos(spreadsheetId: String): List<WorksheetStorageInfo> {
        return database.standDao().getWorksheets(spreadsheetId)
    }

    suspend fun getProductStandsForSpreadsheetWithWorksheetName(
        spreadsheetId: String,
        worksheetName: String
    ): List<ProductAndStand> {
        return database.productFittingDao()
            .getProductsAndStands(spreadsheetId, worksheetName)
    }

    suspend fun writeProductIntoSpreadsheet(
        spreadsheetId: String,
        worksheetName: String,
        productStand: ProductStand
    ) {
        val row = database.productFittingDao().getRowInSpreadSheet(spreadsheetId, productStand.productOwnerId)
        val productStandData = ProductStandDataWritableToSpreadsheet(row, productStand)

        val rangeBuilder = RangeBuilder(workSheetName = worksheetName)
            .start(Settings.Stand.StartColumn, productStandData.row)
            .end(Settings.Stand.EndColumn)

        googleSheetsService.writeSpreadSheet(
            spreadsheetId = spreadsheetId,
            rangeBuilder = rangeBuilder,
            values = mutableListOf(productStandData.toDataList()),
        )
        database.standDao().upsertProductStand(productStand)
    }

    suspend fun getProductStand(
        spreadsheetId: String,
        worksheetName: String,
        id:Int,
        ): ProductStand? {
        return database.standDao().getProductStand(
            spreadsheetId = spreadsheetId,
            worksheetName = worksheetName,
            productId = id,
        )
    }


}
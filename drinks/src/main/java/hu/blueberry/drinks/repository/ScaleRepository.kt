package hu.blueberry.drinks.repository

import hu.blueberry.drive.base.ResourceState
import hu.blueberry.drive.base.handleWithFlow
import hu.blueberry.drive.services.GoogleSheetsService
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.dao.ProductIdAndRowInSpreadSheet
import hu.blueberry.persistentstorage.model.updatedextradata.product.ScaleInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScaleRepository @Inject constructor(
    private val googleSheetsService: GoogleSheetsService,
    private val database: Database,
    private val templateFunctionsRepository: TemplateFunctionsRepository
) {

    suspend fun readScaleFlow(
        spreadSheetId: String,
        worksheetName: String
    ): Flow<ResourceState<Unit>> {
        return handleWithFlow { readScale(spreadSheetId, worksheetName) }
    }


    suspend fun readScale(spreadSheetId: String, worksheetName: String) {
        templateFunctionsRepository.readItems<ScaleInfo>(
            spreadSheetId = spreadSheetId,
            worksheetName = worksheetName,
            startColumn = "D",
            endColumn = "G",
            parseItem = ::parseScaleInfo,
            updateCacheItem = ::updateCachedScaleInfo,
        )
    }

    private fun parseScaleInfo(
        row: MutableList<Any>,
        productAndRow: ProductIdAndRowInSpreadSheet
    ): ScaleInfo {
        return ScaleInfo(
            id = null,
            productOwnerId = productAndRow.productId,
            fullWeight = templateFunctionsRepository
                .stringToDoubleHandle(
                    row.getOrNull(0)
                ) ?: 0.0,
            emptyWeight = templateFunctionsRepository
                .stringToDoubleHandle(
                    row.getOrNull(1)
                ) ?: 0.0,
            containerCapacity = templateFunctionsRepository
                .stringToDoubleHandle(
                    row.getOrNull(3)
                ) ?: 1.0
        )
    }


    private fun updateCachedScaleInfo(scaleInfo: ScaleInfo) {
        templateFunctionsRepository.updateCachedItemOrInsertIfNotExists(
            newItem = scaleInfo,
            getItemFromDatabase = {
                database.scaleInfoDao().getScaleInfoOfProduct(it.productOwnerId)
            },
            insertNotYetCachedItem = { database.scaleInfoDao().upsertScaleInfo(it) },
            updateUpdatedCachedItem = { cached, new ->
                database.scaleInfoDao().upsertScaleInfo(
                    cached.copy(
                        fullWeight = new.fullWeight,
                        emptyWeight = new.emptyWeight,
                        containerCapacity = new.containerCapacity
                    )
                )
            }
        )
    }



}
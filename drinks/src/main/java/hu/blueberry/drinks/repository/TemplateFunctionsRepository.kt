package hu.blueberry.drinks.repository

import hu.blueberry.drive.services.GoogleSheetsService
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.dao.ProductIdAndRowInSpreadSheet
import javax.inject.Inject

class TemplateFunctionsRepository @Inject constructor(
    private val database: Database,
    private val googleSheetsService: GoogleSheetsService,
){
    suspend fun <T> readItems(
        spreadSheetId: String,
        worksheetName: String,
        startColumn: String,
        endColumn: String,
        parseItem: (MutableList<Any>, ProductIdAndRowInSpreadSheet) -> T,
        updateCacheItem: (T) -> Unit,
        loadRowsInSpreadSheet: ()->Unit = {}
    ) {
        //Get products and rows where they are
        var productIdAndRowList =
            database.productFittingDao().getAllProductIdsAndRowsInSpreadsheet(spreadSheetId)
        productIdAndRowList = productIdAndRowList.sortedBy { it.rowInSpreadSheet }.toMutableList()

        if (productIdAndRowList.isEmpty()){

        }
        //Get the start row since we need to start with this one
        var startRow = productIdAndRowList[0].rowInSpreadSheet

        //create the reading range
        val range = "$worksheetName!$startColumn$startRow:$endColumn"
        var index = startRow

        val valueRange = googleSheetsService.readSpreadSheetFormula(spreadSheetId, range) ?: TODO()
        for (row in valueRange.getValues()){
            //If there is a product's info in that row
            if (index == startRow) {
                //get out the info from the list to use and to be able to get the next one
                val productAndRow = productIdAndRowList.removeAt(0)

                updateCacheItem(parseItem(row, productAndRow))

                //if not empty go to next product
                if (productIdAndRowList.isNotEmpty()){
                    startRow = productIdAndRowList[0].rowInSpreadSheet
                }
            }
            //set the index to the next row
            index += 1
        }
    }

    fun stringToDoubleHandle(any: Any?): Double?{
        return try {
            any?.toString()?.toDouble()
        }catch (e: NumberFormatException){
            null
        }
    }

    fun <T> updateCachedItemOrInsertIfNotExists(
        newItem: T,
        getItemFromDatabase: (T) -> T?,
        insertNotYetCachedItem: (T) -> Unit,
        updateUpdatedCachedItem: (cachedItem:T, newItem:T) -> Unit,
    ) {
        val cachedInfo = getItemFromDatabase(newItem)
        if (cachedInfo == null) {
            insertNotYetCachedItem(newItem)
        } else {
            updateUpdatedCachedItem(cachedInfo, newItem)
        }
    }
}
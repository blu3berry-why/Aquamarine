package hu.blueberry.drinks.model

import hu.blueberry.persistentstorage.model.updatedextradata.WorkingDirectory
import hu.blueberry.persistentstorage.model.updatedextradata.spreadsheet.ProductStandDataWritableToSpreadsheet

data class MemoryDatabase2(
    var workingDirectory: WorkingDirectory,
    var selectedWorksheet: String? = null
){
    fun runCodeIfSpreadsheetIdNotNull(request: (String)-> Unit){
        workingDirectory.choosenSpreadSheet?.id?.let(request)
    }
    fun runCodeIfWorksheetNameNotNull(request: (String)-> Unit){
        selectedWorksheet?.let(request)

    }
}

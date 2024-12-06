package hu.blueberry.drive.repositories

import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse
import com.google.api.services.sheets.v4.model.UpdateValuesResponse
import com.google.api.services.sheets.v4.model.ValueRange
import hu.blueberry.drive.base.ResourceState
import hu.blueberry.drive.base.handleWithFlow
import hu.blueberry.drive.services.GoogleSheetsService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoogleSheetRepository @Inject constructor(
    private var googleSheetsManager: GoogleSheetsService
) {

    suspend fun readSpreadSheet(spreadSheetId:String, range:String): Flow<ResourceState<ValueRange?>> {
        return handleWithFlow { googleSheetsManager.readSpreadSheet(spreadSheetId,range) }
    }


    suspend fun updateSpreadSheet(spreadSheetId:String, valueRange: ValueRange): Flow<ResourceState<UpdateValuesResponse?>> {
        return handleWithFlow { googleSheetsManager.updateSpreadSheet(spreadSheetId, valueRange) }
    }

    suspend fun createWorksheet(spreadSheetId: String, name: String): Flow<ResourceState<BatchUpdateSpreadsheetResponse>> {
        return handleWithFlow { googleSheetsManager.createNewWorksheet(spreadSheetId, name) }
    }

    suspend fun listWorksheetNames(spreadSheetId: String): Flow<ResourceState<List<String>>> {
        return handleWithFlow { googleSheetsManager.listWorkSheetNames(spreadSheetId) }
    }
}
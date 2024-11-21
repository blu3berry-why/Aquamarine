package hu.blueberry.drive.services

import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.AddSheetRequest
import com.google.api.services.sheets.v4.model.AppendValuesResponse
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse
import com.google.api.services.sheets.v4.model.Request
import com.google.api.services.sheets.v4.model.SheetProperties
import com.google.api.services.sheets.v4.model.Spreadsheet
import com.google.api.services.sheets.v4.model.SpreadsheetProperties
import com.google.api.services.sheets.v4.model.UpdateSheetPropertiesRequest
import com.google.api.services.sheets.v4.model.UpdateValuesResponse
import com.google.api.services.sheets.v4.model.ValueRange
import hu.blueberry.drive.base.CloudBase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleSheetsService @Inject constructor(
    private var cloudBase: CloudBase,
) {
    var scopes = listOf(SheetsScopes.SPREADSHEETS)

    //TODO What if the user is not signed in?
    private fun getSheetsService(): Sheets? {
        return Sheets.Builder(
            NetHttpTransport(), GsonFactory.getDefaultInstance(),
            cloudBase.getCredentials(scopes)
        ).build()
    }

    private val sheets: Sheets
        get() = getSheetsService()!!

    object InputOption {
        val RAW = "RAW"
        val USER_ENTERED = "USER_ENTERED"
        val FORMULA = "FORMULA"
    }

    val MAJOR_DIMENSION = "ROWS"


    fun createSheet(name: String): String? {
        var spreadsheet = Spreadsheet()
        spreadsheet.properties = SpreadsheetProperties()
            .apply { title = name }

        spreadsheet = sheets.spreadsheets().create(spreadsheet).execute()
        spreadsheet.properties

        return spreadsheet.spreadsheetId
    }

    suspend fun readSpreadSheet(
        spreadsheetId: String,
        range: String,
    ): ValueRange? {
        var result: ValueRange? = null

        result = sheets.spreadsheets().values().get(spreadsheetId, range).execute()

        return result
    }

    suspend fun readSpreadSheetFormula(
        spreadsheetId: String,
        range: String,
    ): ValueRange? {
        var valueRange: ValueRange? = null

        val spreadsheet = sheets.spreadsheets().values().get(spreadsheetId, range)
        spreadsheet.valueRenderOption = InputOption.FORMULA

        valueRange = spreadsheet.execute()

        return valueRange
    }

    fun writeSpreadSheet(
        spreadsheetId: String,
        range: String,
        values: MutableList<MutableList<Any>>,
    ): UpdateValuesResponse? {

        var result: UpdateValuesResponse? = null
        val body = ValueRange()
        body.setValues(values)
        body.majorDimension = MAJOR_DIMENSION
        result = sheets.spreadsheets().values()
            .update(spreadsheetId, range, body)
            .apply {
                valueInputOption = InputOption.USER_ENTERED
            }
            .execute()
        return result
    }

    fun updateSpreadSheet(spreadsheetId: String, valueRange: ValueRange): UpdateValuesResponse? {
        return sheets.spreadsheets().values().update(spreadsheetId, valueRange.range, valueRange)
            .apply { valueInputOption = InputOption.USER_ENTERED }.execute()
    }

    fun appendToSpreadSheet(
        spreadsheetId: String,
        range: String,
        values: MutableList<MutableList<Any>>,
    ): AppendValuesResponse? {

        var result: AppendValuesResponse? = null
        val body = ValueRange()
        body.setValues(values)
        body.majorDimension = MAJOR_DIMENSION
        result = sheets.spreadsheets().values()
            .append(spreadsheetId, range, body)
            .apply {
                valueInputOption = InputOption.USER_ENTERED
            }
            .execute()
        return result
    }

    fun createNewWorksheet(spreadsheetId: String, name: String): BatchUpdateSpreadsheetResponse {
        var spreadsheet: BatchUpdateSpreadsheetResponse = BatchUpdateSpreadsheetResponse()
        val request = Request()
            .setAddSheet(
                AddSheetRequest()
                    .apply {
                        this.properties = SheetProperties()
                            .apply { this.title = name }
                    }
            )
        val batch = BatchUpdateSpreadsheetRequest().setRequests(listOf(request))

        spreadsheet = sheets.spreadsheets().batchUpdate(spreadsheetId, batch).execute()
        return spreadsheet
    }

    fun renameWorksheet(
        spreadsheetId: String,
        oldName: String,
        newName: String,
    ): BatchUpdateSpreadsheetResponse? {
        var spreadsheet = BatchUpdateSpreadsheetResponse()
        val sheet = sheets.spreadsheets().get(spreadsheetId).execute()

        val sheetId = sheet.sheets.filter { it.properties.title == oldName }
            .firstOrNull()?.properties?.sheetId ?: return null


        val request = Request().apply {
            updateSheetProperties = UpdateSheetPropertiesRequest().apply {
                this.properties = SheetProperties().apply {
                    this.sheetId = sheetId
                    this.title = newName
                }
            }
        }

        val batch = BatchUpdateSpreadsheetRequest().setRequests(listOf(request))

        spreadsheet = sheets.spreadsheets().batchUpdate(spreadsheetId, batch).execute()
        return spreadsheet
    }

    fun initializeFirstTab(
        spreadsheetId: String,
        newName: String,
    ): BatchUpdateSpreadsheetResponse? {
        var spreadsheet = BatchUpdateSpreadsheetResponse()
        val sheet = sheets.spreadsheets().get(spreadsheetId).execute()

        val sheetId = sheet.sheets[0].properties.sheetId

        val request = Request().apply {
            updateSheetProperties = UpdateSheetPropertiesRequest().apply {
                this.properties = SheetProperties().apply {
                    this.sheetId = sheetId
                    this.title = newName
                }
                this.fields = "title"
            }
        }

        val batch = BatchUpdateSpreadsheetRequest().setRequests(listOf(request))

        spreadsheet = sheets.spreadsheets().batchUpdate(spreadsheetId, batch).execute()

        writeSpreadSheet(
            spreadsheetId,
            "${newName}!A1:B1",
            mutableListOf(mutableListOf("Név", "Hányad"))
        )
        return spreadsheet
    }


    fun listWorkSheetNames(spreadsheetId: String): List<String> {
        val spreadsheet = sheets.spreadsheets().get(spreadsheetId).execute()
        return spreadsheet.sheets.map { it.properties.title }
    }

}
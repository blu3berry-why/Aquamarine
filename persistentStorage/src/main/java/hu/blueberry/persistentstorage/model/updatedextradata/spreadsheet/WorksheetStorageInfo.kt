package hu.blueberry.persistentstorage.model.updatedextradata.spreadsheet

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("worksheet_storage_info")
data class WorksheetStorageInfo(
    @PrimaryKey val id :Int?,
    @ColumnInfo("spreadsheet_id") val spreadSheetId: String,
    @ColumnInfo("worksheet_name") val worksheetName:String,
)

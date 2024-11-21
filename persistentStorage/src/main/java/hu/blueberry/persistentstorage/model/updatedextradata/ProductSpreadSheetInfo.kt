package hu.blueberry.persistentstorage.model.updatedextradata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_spreadsheet_info")
data class ProductSpreadSheetInfo(
    @PrimaryKey val productSpreadSheetId: Int,
    val productOwnerId: Int,
    @ColumnInfo("row_in_spreadsheet") val rowInSpreadSheet: Int,
    @ColumnInfo("spreadsheet_id") val spreadSheetId: String
)

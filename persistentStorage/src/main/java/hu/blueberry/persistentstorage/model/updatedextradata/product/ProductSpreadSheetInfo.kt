package hu.blueberry.persistentstorage.model.updatedextradata.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_spreadsheet_info")
data class ProductSpreadSheetInfo(
    @PrimaryKey val id: Int?,
    @ColumnInfo("product_properties_id") val productPropertiesId: Int,
    @ColumnInfo("row_in_spreadsheet") val rowInSpreadSheet: Int,
    @ColumnInfo("spreadsheet_id") val spreadSheetId: String,
)

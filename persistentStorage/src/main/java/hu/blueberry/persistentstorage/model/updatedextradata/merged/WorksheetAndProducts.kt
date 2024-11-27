package hu.blueberry.persistentstorage.model.updatedextradata.merged

import androidx.room.Embedded
import androidx.room.Relation
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductStand
import hu.blueberry.persistentstorage.model.updatedextradata.spreadsheet.WorksheetStorageInfo

data class WorksheetAndProductStands(
    @Embedded val worksheetStorageInfo: WorksheetStorageInfo,
    @Relation(
        parentColumn = "id",
        entityColumn = "worksheet_id"
    )
    val productStands: List<ProductStand>
)

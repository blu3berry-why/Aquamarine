package hu.blueberry.persistentstorage.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import hu.blueberry.persistentstorage.model.updatedextradata.merged.WorksheetAndProductStands
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductStand

@Dao
interface StandDao {
    @Upsert
    fun upsertProductStand(vararg productStand: ProductStand)

    @Upsert
    fun upsertProductStand(productStand: ProductStand)

    @Query("""
        SELECT * FROM product_stand WHERE worksheet_id=:worksheetId AND productOwnerId = :productId
    """)
    fun getProductStandByWorksheetIdAndProductId(worksheetId:Int, productId:Int):ProductStand?

    @Transaction
    @Query("SELECT * FROM worksheet_storage_info WHERE spreadsheet_id = :spreadsheetId")
    fun getWorksheetAndProductStandsForSpreadsheet(spreadsheetId: String): List<WorksheetAndProductStands>


    /**
     * @return the row which the first element starts in
     */
    @Query("""
        SELECT row_in_spreadsheet 
        FROM product_spreadsheet_info 
        WHERE spreadsheet_id = :spreadsheetId
        ORDER BY row_in_spreadsheet
        LIMIT 1
        """)
    fun getStartingRowForStorage(spreadsheetId: String): Int

}
package hu.blueberry.persistentstorage.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import hu.blueberry.persistentstorage.model.updatedextradata.merged.WorksheetAndProductStands
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductStand
import hu.blueberry.persistentstorage.model.updatedextradata.spreadsheet.WorksheetStorageInfo

@Dao
interface StandDao {
    @Upsert
    fun upsertProductStand(vararg productStand: ProductStand)

    @Upsert
    fun upsertProductStand(productStand: ProductStand)

    @Upsert
    fun upsertWorksheetStorageInfo(worksheetStorageInfo: WorksheetStorageInfo)

    @Query("SELECT * FROM worksheet_storage_info WHERE spreadsheet_id = :spreadsheetId")
    fun getWorksheets(spreadsheetId: String): List<WorksheetStorageInfo>

    @Query("""
        SELECT * FROM product_stand WHERE worksheet_id=:worksheetId AND productOwnerId = :productId
    """)
    fun getProductStand(worksheetId:Int, productId:Int):ProductStand?

    @Query("""
        SELECT * FROM product_stand 
        JOIN worksheet_storage_info on worksheet_id = worksheet_storage_info.id
        WHERE worksheet_name=:worksheetName AND productOwnerId = :productId AND spreadsheet_id = :spreadsheetId
    """)
    fun getProductStand(spreadsheetId: String, worksheetName:String, productId:Int):ProductStand?

    @Transaction
    @Query("SELECT * FROM worksheet_storage_info WHERE spreadsheet_id = :spreadsheetId")
    fun getWorksheetAndProductStands(spreadsheetId: String): List<WorksheetAndProductStands>

    @Transaction
    @Query("""
        SELECT * FROM worksheet_storage_info 
        WHERE spreadsheet_id = :spreadsheetId 
        AND worksheet_name = :worksheetName""")
    fun getWorksheetAndProductStands(spreadsheetId: String, worksheetName:String): WorksheetAndProductStands?


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


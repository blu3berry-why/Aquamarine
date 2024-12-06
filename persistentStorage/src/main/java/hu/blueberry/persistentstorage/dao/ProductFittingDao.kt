package hu.blueberry.persistentstorage.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import hu.blueberry.persistentstorage.model.updatedextradata.merged.ProductAndScaleInfo
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductProperties
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductSpreadSheetInfo
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductStand

@Dao
interface ProductFittingDao {
    @Query(
        """
        SELECT product_properties.id AS productId,
        product_spreadsheet_info.row_in_spreadsheet AS rowInSpreadSheet
        FROM product_properties, product_spreadsheet_info
        WHERE product_properties.id = product_spreadsheet_info.product_properties_id AND product_spreadsheet_info.spreadsheet_id = :spreadSheetId
        """
    )
    fun getAllProductIdsAndRowsInSpreadsheet(spreadSheetId: String): List<ProductIdAndRowInSpreadSheet>

    @Query(
        """
        SELECT product_spreadsheet_info.row_in_spreadsheet 
        FROM product_properties, product_spreadsheet_info
        WHERE product_properties.id = product_spreadsheet_info.product_properties_id AND product_spreadsheet_info.spreadsheet_id = :spreadSheetId AND product_properties.id = :productId
        """
    )
    fun getRowInSpreadSheet(spreadSheetId: String, productId: Int): Int

    @Transaction
    @Query("""SELECT * FROM product_properties""")
    fun getProductsAndScaleInfos(): List<ProductAndScaleInfo>

    @Query(
        """
            SELECT * FROM product_properties 
            JOIN product_spreadsheet_info on product_properties_id = product_properties.id 
            WHERE spreadsheet_id = :spreadSheetId  
        """
    )
    fun getProductProperties(spreadSheetId: String): List<ProductProperties>

    @Transaction
    @Query(
        """
            SELECT * FROM product_stand
            JOIN worksheet_storage_info on worksheet_storage_info.id = worksheet_id
            WHERE worksheet_name = :worksheetName AND spreadsheet_id = :spreadSheetId
        """
    )
    fun getProductsAndStands(spreadSheetId: String, worksheetName:String):List<ProductAndStand>
}

data class ProductIdAndRowInSpreadSheet(
    val productId: Int,
    val rowInSpreadSheet: Int,
)

data class ProductAndSpreadsheetInfo(
    val product: ProductProperties,
    val spreadSheetInfo: ProductSpreadSheetInfo
)

data class ProductAndStand(
    @Embedded
    val stand: ProductStand,
    @Relation(
        parentColumn = "productOwnerId",
        entityColumn = "id"
    )
    val product: ProductProperties?,
)
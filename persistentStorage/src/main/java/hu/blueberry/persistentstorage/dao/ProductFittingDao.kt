package hu.blueberry.persistentstorage.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import hu.blueberry.persistentstorage.model.updatedextradata.merged.ProductAndScaleInfo

@Dao
interface ProductFittingDao {
@Query(
    """
        SELECT product_properties.id AS productId, product_spreadsheet_info.row_in_spreadsheet AS rowInSpreadSheet
        FROM product_properties, product_spreadsheet_info
        WHERE product_properties.id = product_spreadsheet_info.product_properties_id AND product_spreadsheet_info.spreadsheet_id = :spreadSheetId
    """
)
fun loadAllProductIdsAndRowsInSpreadsheet(spreadSheetId: String): List<ProductIdAndRowInSpreadSheet>

    @Transaction
    @Query("SELECT * FROM product_properties")
    fun getProductsAndScaleInfos(): List<ProductAndScaleInfo>

}

data class ProductIdAndRowInSpreadSheet(
    val productId: Int,
    val rowInSpreadSheet: Int,
)
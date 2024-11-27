package hu.blueberry.persistentstorage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductSpreadSheetInfo

@Dao
interface ProductSpreadSheetInfoDao {

    @Query("""
        SELECT * FROM product_spreadsheet_info WHERE product_spreadsheet_info.spreadsheet_id = :spreadSheetId
        """
    )
    fun loadAllProductSpreadSheetInfoWithSpreadSheetId(spreadSheetId: String): List<ProductSpreadSheetInfo>

     @Delete
     fun deleteProductSpreadSheetInfo(productSpreadSheetInfo: ProductSpreadSheetInfo)

     @Upsert
     fun upsertProductSpreadSheetInfo(vararg productSpreadSheetInfo: ProductSpreadSheetInfo)

}


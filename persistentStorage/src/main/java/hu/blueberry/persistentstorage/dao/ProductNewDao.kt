package hu.blueberry.persistentstorage.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import hu.blueberry.persistentstorage.model.updatedextradata.merged.ProductAndScaleInfo

@Dao
interface ProductNewDao {
    @Transaction
    @Query("SELECT * FROM product_properties")
    fun getProductsAndScaleInfos(): List<ProductAndScaleInfo>
}
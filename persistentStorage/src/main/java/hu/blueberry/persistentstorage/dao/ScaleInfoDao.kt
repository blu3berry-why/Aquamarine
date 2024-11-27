package hu.blueberry.persistentstorage.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import hu.blueberry.persistentstorage.model.updatedextradata.product.ScaleInfo

@Dao
interface ScaleInfoDao {
    @Query("""
        SELECT * FROM scale_info WHERE scale_info.productOwnerId = :productId
    """)
    fun getScaleInfoOfProduct(productId: Int): ScaleInfo?

    @Upsert
    fun upsertScaleInfo(scaleInfo: ScaleInfo)

}
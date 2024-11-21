package hu.blueberry.persistentstorage.model.updatedextradata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.blueberry.persistentstorage.model.MeasureUnit
import hu.blueberry.persistentstorage.model.ProductType

@Entity(tableName = "product_properties")
data class ProductProperties(
    @PrimaryKey
    val productId: Int? = null,
    @ColumnInfo("product_name") val name: String,
    @ColumnInfo("product_type") val type: ProductType,
    @ColumnInfo("measure_unit") val measureUnit: MeasureUnit,
    @ColumnInfo("selling_price") val sellingPrice: Int,
    /**
     *  **For example:** the beer is stored by liter but sold by 0.5 liters/purchase
     */
    @ColumnInfo("selling_quantity_per_purchase") val sellingQuantityForPurchasedSingleProduct: Double,
 )

package hu.blueberry.persistentstorage.model.updatedextradata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_stand")
data class ProductStand(
    @PrimaryKey val standId: Int,
    val productOwnerId: Int,
    @ColumnInfo("opening_stock") val openingStock: Double,
    @ColumnInfo("stock_change") val stockChange: Double,
    @ColumnInfo("closing_stock") val closingStock: Double,
)

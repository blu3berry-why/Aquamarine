package hu.blueberry.persistentstorage.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import hu.blueberry.persistentstorage.model.converter.Converters


@Entity(tableName = "products")
@TypeConverters(Converters::class)
data class Product(
    @PrimaryKey var id: Long? = null,
    var name: String? = null,
    var measureUnit: MeasureUnit = MeasureUnit.PIECE,
    var productType: ProductType = ProductType.OTHER,
    val storages: MutableList<StorageData> = mutableListOf( StorageData(name = "F17"), StorageData(name = "F21"), StorageData(name = "Elso_emelet")),
    var price: Int? = 0,
    var purchasePrice: Int? = 0,
) {
    companion object {
        fun convertFromArray(list: List<String>): Product {
            return when (list.size) {
                0 -> Product()
                1 -> Product(name = list[0])
                else -> Product(name = list[0], measureUnit = MeasureUnit.fromString(list[1]))
            }
        }
    }
}

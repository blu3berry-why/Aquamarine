package hu.blueberry.persistentstorage.model.updatedextradata.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "scale_info")
data class ScaleInfo(
    @PrimaryKey val id: Int?,
    val productOwnerId: Int,
    @ColumnInfo("full_weight") val fullWeight: Double,
    @ColumnInfo("empty_weight") val emptyWeight: Double,
    /**
     * How much units does the container hold
     * **For example:**
     *  - Berentzen 70 (cl)
     *  - Beer 50 (l)
     *  ...
     */
    @ColumnInfo("container_capacity") val containerCapacity: Double,
) {
    val weightPerOneUnit: Double
        get() {
            return (fullWeight - emptyWeight) / containerCapacity
        }
}
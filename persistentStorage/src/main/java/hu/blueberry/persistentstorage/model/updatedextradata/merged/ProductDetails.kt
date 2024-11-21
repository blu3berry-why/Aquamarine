package hu.blueberry.persistentstorage.model.updatedextradata.merged

import androidx.room.Embedded
import androidx.room.Relation
import hu.blueberry.persistentstorage.model.updatedextradata.ProductProperties
import hu.blueberry.persistentstorage.model.updatedextradata.ScaleInfo

data class ProductAndScaleInfo(
    @Embedded val productProperties: ProductProperties,
    @Relation(
        parentColumn = "productId",
        entityColumn = "productOwnerId"
    )
    val scaleInfo: ScaleInfo
)

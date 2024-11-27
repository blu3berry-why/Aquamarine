package hu.blueberry.persistentstorage.model.updatedextradata.merged

import androidx.room.Embedded
import androidx.room.Relation
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductProperties
import hu.blueberry.persistentstorage.model.updatedextradata.product.ScaleInfo

data class ProductAndScaleInfo(
    @Embedded val productProperties: ProductProperties,
    @Relation(
        parentColumn = "id",
        entityColumn = "productOwnerId"
    )
    val scaleInfo: ScaleInfo
)

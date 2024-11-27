package hu.blueberry.persistentstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.blueberry.persistentstorage.dao.ProductDao
import hu.blueberry.persistentstorage.dao.ProductFittingDao
import hu.blueberry.persistentstorage.dao.ProductNewDao
import hu.blueberry.persistentstorage.dao.ProductSpreadSheetInfoDao
import hu.blueberry.persistentstorage.dao.ScaleInfoDao
import hu.blueberry.persistentstorage.dao.StandDao
import hu.blueberry.persistentstorage.model.Product
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductProperties
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductSpreadSheetInfo
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductStand
import hu.blueberry.persistentstorage.model.updatedextradata.product.ScaleInfo
import hu.blueberry.persistentstorage.model.updatedextradata.spreadsheet.WorksheetStorageInfo

@Database(
    version = 10, entities = [
        Product::class,
        ProductProperties::class,
        ScaleInfo::class,
        ProductSpreadSheetInfo::class,
        WorksheetStorageInfo::class,
        ProductStand::class,
    ]
)
abstract class Database : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productNewDao(): ProductNewDao
    abstract fun productFittingDao(): ProductFittingDao
    abstract fun productSpreadSheetInfoDao(): ProductSpreadSheetInfoDao
    abstract fun scaleInfoDao(): ScaleInfoDao
    abstract fun standDao(): StandDao
}
package hu.blueberry.persistentstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.blueberry.persistentstorage.dao.ProductDao
import hu.blueberry.persistentstorage.dao.ProductNewDao
import hu.blueberry.persistentstorage.model.Product
import hu.blueberry.persistentstorage.model.updatedextradata.ProductProperties
import hu.blueberry.persistentstorage.model.updatedextradata.ScaleInfo

@Database(version = 3, entities = [
    Product::class,
    ProductProperties::class,
    ScaleInfo::class,
])
abstract class Database : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productNewDao():ProductNewDao
}
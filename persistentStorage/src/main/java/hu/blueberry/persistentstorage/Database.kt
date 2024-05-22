package hu.blueberry.persistentstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.blueberry.persistentstorage.dao.ProductDao
import hu.blueberry.persistentstorage.model.Product

@Database(version = 1, entities = [Product::class])
abstract class Database : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
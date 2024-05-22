package hu.blueberry.persistentstorage.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import hu.blueberry.persistentstorage.model.Product

@Dao
interface ProductDao {
    @Insert
    fun insertAll(vararg products: Product)

    @Insert
    fun insert(product: Product) : Long

    @Update
    fun update(product: Product)

    @Query("SELECT * FROM products")
    fun getAll():List<Product>

    @Query("SELECT * FROM products WHERE id=:id")
    fun getProductById(id :Long): Product
}
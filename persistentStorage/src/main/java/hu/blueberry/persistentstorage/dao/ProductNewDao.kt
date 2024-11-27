package hu.blueberry.persistentstorage.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductProperties

@Dao
interface ProductNewDao {


    @Query("SELECT * FROM product_properties")
    fun getAllProductProperties():MutableList<ProductProperties>

    @Query("SELECT * FROM product_properties WHERE product_name = :productName")
    fun getProductByName(productName:String): ProductProperties

    @Upsert
    fun upsertProductProperty(properties: ProductProperties)

    @Upsert
    fun upsertProductPropertyList(propertyList: List<ProductProperties>)


}
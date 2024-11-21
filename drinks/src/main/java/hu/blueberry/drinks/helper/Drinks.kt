package hu.blueberry.drinks.helper

import hu.blueberry.persistentstorage.model.Product
import hu.blueberry.persistentstorage.model.ProductType

class Drinks(list: List<Product>) {

    val productsByCategory : MutableList<ProductTypeList> = mutableListOf()

    init {
        for (productType in ProductType.entries){
            productsByCategory.add(ProductTypeList(productType))
        }

        for (productList in productsByCategory){
            productList.addAll(list.filter { it.productType == productList.productType })
        }
    }

    fun toList(workSheetName:String): MutableList<List<Any>> {
        val outList: MutableList<List<Any>> = mutableListOf()

        for (productList in productsByCategory){
            outList.addAll(productList.writeToList(workSheetName))
        }
        return outList

    }
}
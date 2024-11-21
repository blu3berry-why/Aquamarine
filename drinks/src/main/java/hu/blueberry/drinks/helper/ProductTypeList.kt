package hu.blueberry.drinks.helper

import hu.blueberry.persistentstorage.model.Product
import hu.blueberry.persistentstorage.model.ProductType

class ProductTypeList(var productType: ProductType) {

    val products: MutableList<Product> = mutableListOf()

    fun add(product: Product) {
        if (product.productType == productType) {
            if (products.contains(product)) {
                return
            }
            products.add(product)
        } else {
            throw Exception("Product with type \"${product.productType.displayString}\" can't be added to list with product types: \"${productType.displayString}\"!")
        }
    }

    fun addAll(productList: List<Product>) {
        for (product in productList) {
            add(product)
        }
    }

    fun writeToList(workSheetName: String): MutableList<List<Any>> {
        val outList: MutableList<List<Any>> = mutableListOf()
        // Leave space for the header row
        outList.add(listOf())

        for (product in products) {
            outList.add(product.toRowList(workSheetName))
        }

        return outList
    }
}
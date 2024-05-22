package hu.blueberry.drinks.helper

import com.google.api.services.sheets.v4.model.ValueRange
import hu.blueberry.persistentstorage.model.Product
import hu.blueberry.persistentstorage.model.ProductType
import hu.blueberry.persistentstorage.model.StorageData

fun productListFromValueRange(data: ValueRange): List<Product> {
    val productList: MutableList<Product> = mutableListOf()

    for (values in data.values) {
        /*
        * There are 3 components:
        * majorDimension: String
        * range: String
        * values: ArrayList<ArrayList<String>>
        * */
        if (values is ArrayList<*>) {
            var pType: ProductType = ProductType.OTHER
            // This should be the rows
            for (row in values) {
                val list = row as List<String>
                //Check if it is a label
                if (ProductType.isProductType(row[0])) {
                    pType = ProductType.fromString(row[0])
                } else {
                    productList.add(
                        Product.convertFromArray(list)
                            .apply { this.productType = pType }
                    )
                }
            }
        }
    }
    return productList
}


fun convertStringToInt(string: String): Int {
    return try {
        string.toInt()
    } catch (_: Exception) {
        0
    }
}


fun StorageData.setValues(open: Int = 0, cart: Int = 0, close: Int = 0) {
    this.open = open
    this.cart = cart
    this.close = close
}


fun StorageData.addValuesFromList(list: List<String>) {
    when (list.size) {
        3 -> setValues(convertStringToInt(list[2]))
        4 ->
            setValues(
                convertStringToInt(list[2]),
                convertStringToInt(list[3])
            )

        5 -> setValues(
            convertStringToInt(list[2]),
            convertStringToInt(list[3]),
            convertStringToInt(list[4]),
        )

        else -> setValues()
    }
}

fun productListFromValueRangeTyped(data: ValueRange): List<Product> {
    val range = data.range

    val productList: MutableList<Product> = mutableListOf()
    val fromArrayStringList: MutableList<MutableList<String>> = mutableListOf()
    var pType: ProductType = ProductType.OTHER

    //Convert to List of List of Strings to have types
    for (array in data.getValues()) {
        val stringArray: MutableList<String> = mutableListOf()
        for (string in array) {
            stringArray.add(string.toString())
        }
        fromArrayStringList.add(stringArray)
    }

    //One row is one row from the spreadsheet
    for (row in fromArrayStringList) {
        if (ProductType.isProductType(row[0])) {
            pType = ProductType.fromString(row[0])
        } else {
            val product = Product.convertFromArray(row)
                .apply { this.productType = pType }

            //TODO This is ineffective when proper Worksheet management is created I need to update this
            val storageData = product.storages.firstOrNull { range.contains(it.name) }

            storageData?.addValuesFromList(row)

            productList.add(product)
        }
    }
    return productList
}




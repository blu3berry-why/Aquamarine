package hu.blueberry.persistentstorage.model.updatedextradata.spreadsheet

import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductStand

data class ProductStandDataWritableToSpreadsheet(
    val row: Int,
    var openingStock: Double,
    var stockChange: Double,
    var closingStock: Double,
    ) {

    constructor(
        row : Int,
        productStand: ProductStand,
    ) : this(
        row = row,
        openingStock = productStand.openingStock,
        stockChange = productStand.stockChange,
        closingStock = productStand.closingStock
    )


    fun toDataList(): MutableList<Any> {
        return mutableListOf(openingStock, stockChange, closingStock)
    }
}
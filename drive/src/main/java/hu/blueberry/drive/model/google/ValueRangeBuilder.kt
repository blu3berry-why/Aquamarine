package hu.blueberry.drive.model.google

import com.google.api.services.sheets.v4.model.ValueRange

class ValueRangeBuilder(var majorDimension: MajorDimension, private var range: Range) {
    private val values: MutableList<List<Any>> = mutableListOf()

    fun addRow(list: List<Any>){
        values.add(list)
    }

    fun addAll(list: List<List<Any>>){
        values.addAll(list)
    }



    fun build(): ValueRange {
        val valueRange = ValueRange()
        valueRange.range = range.build()
        valueRange.majorDimension = majorDimension.dimension
        valueRange.setValues(values)
        return valueRange
    }


}
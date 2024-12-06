package hu.blueberry.drive.model.google

import com.google.api.services.sheets.v4.model.ValueRange
import hu.blueberry.drive.model.google.enums.MajorDimension

class ValueRangeBuilder(var majorDimension: MajorDimension, private var rangeBuilder: RangeBuilder) {
    private val values: MutableList<List<Any>> = mutableListOf()

    fun addRow(list: List<Any>){
        values.add(list)
    }

    fun addAll(list: List<List<Any>>){
        values.addAll(list)
    }



    fun build(): ValueRange {
        val valueRange = ValueRange()
        valueRange.range = rangeBuilder.build()
        valueRange.majorDimension = majorDimension.stringValue
        valueRange.setValues(values)
        return valueRange
    }


}
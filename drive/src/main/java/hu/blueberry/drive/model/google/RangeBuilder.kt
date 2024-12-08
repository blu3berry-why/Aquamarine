package hu.blueberry.drive.model.google

class RangeBuilder(
    private var workSheetName:String
) {
    private var start:String = "A1"
    private var end:String? = null

    constructor(
       workSheetName: String,
       start: String,
       end: String?,
    ) : this(workSheetName) {
        this.start = start
        this.end = end
    }

    fun start(colum:String?, row:Int? = null): RangeBuilder {
        start = when{
            colum != null && row == null -> colum
            colum == null && row != null -> row.toString()
            colum != null && row != null -> (colum) + row.toString()
            else -> throw IllegalArgumentException("Start cell value can't be empty!")
        }
        return this
    }

    fun end(colum:String?, row:Int? = null): RangeBuilder {
        end = when{
            colum != null && row == null -> colum
            colum == null && row != null -> row.toString()
            colum != null && row != null -> (colum) + row.toString()
            else -> null
        }
        return this
    }


    fun build():String{
        if (end == null){
            return "'$workSheetName'!$start"
        }
        return "'$workSheetName'!$start:$end"
    }

}
package hu.blueberry.drive.model.google

class RangeBuilder(
    private var workSheetName:String
) {
    private var start:String = "A1"
    private var end:String = "A1"

    constructor(
       workSheetName: String,
       start: String,
       end:String,
    ) : this(workSheetName) {
        this.start = start
        this.end = end
    }

    fun start(colum:String, row:Int? = null): RangeBuilder {
        start = if (row == null){
            colum
        }else{
            (colum) + row.toString()
        }
        return this
    }

    fun end(colum:String, row:Int? = null): RangeBuilder {
        end = if (row == null){
            colum
        }else{
            (colum) + row.toString()
        }
        return this
    }




    fun build():String{
        return "'$workSheetName'!$start:$end"
    }

}
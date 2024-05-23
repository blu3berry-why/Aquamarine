package hu.blueberry.drive.model.google

class Range(var workSheetName:String, var start:String, var end:String) {
    fun build():String{
        return "'$workSheetName'!$start:$end"
    }

}
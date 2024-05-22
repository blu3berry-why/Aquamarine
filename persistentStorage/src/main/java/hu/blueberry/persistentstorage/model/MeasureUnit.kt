package hu.blueberry.persistentstorage.model

enum class MeasureUnit(val displayString:String) {
    BOTTLE("ü"),
    LITER("l"),
    DECILITER("dl"),
    CENTILITER("cl"),
    PIECE("db"),;
    companion object{
        fun fromString(string: String) : MeasureUnit {
            return when(string.lowercase()){
                BOTTLE.displayString -> BOTTLE
                LITER.displayString -> LITER
                DECILITER.displayString -> DECILITER
                CENTILITER.displayString -> CENTILITER
                PIECE.displayString -> PIECE
                else -> throw Exception("There is no type associated with '$string' measure unit. The types are: ${MeasureUnit.entries.joinToString { "${it.displayString}, " }}")
            }
        }
    }

}


package hu.blueberry.camera.models.enums

enum class PhotoClockType ( val textForm:String) {
    FNT_COLD("FNT Hideg"),
    FNT_HOT("FNT Meleg"),
    HOMAR_COLD("Homár Hideg"),
    HOMAR_HOT("Homár Meleg"),
    HOMAR_ELECTRICITY("Homár Áram");

    override fun  toString(): String {
        return textForm
    }
}
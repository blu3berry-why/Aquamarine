package hu.blueberry.camera.models.enums

enum class PhotoTakenTime(val textForm:String) {
    OPENING("Nyitó"),
    CLOSING("Záró");

    override fun  toString(): String {
        return textForm
    }
}
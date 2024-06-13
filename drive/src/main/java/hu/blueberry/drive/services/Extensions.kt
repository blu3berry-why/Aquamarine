package hu.blueberry.drive.services

import android.content.Context
import java.io.File

fun Context.createImageFile(name:String): File {
    // Create an image file name
    val image = File.createTempFile(
        name, /* prefix */
        ".png", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}
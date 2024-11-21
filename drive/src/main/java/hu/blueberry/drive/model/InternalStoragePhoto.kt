package hu.blueberry.drive.model

import android.graphics.Bitmap

data class InternalStoragePhoto(
    val name: String,
    val bitmap: Bitmap,
    val path: String,
)

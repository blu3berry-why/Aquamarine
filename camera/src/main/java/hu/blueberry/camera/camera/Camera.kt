package hu.blueberry.camera.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat

class Camera {

    companion object {

        //static function
        fun takePhoto(
            applicationContext: Context,
            controller: LifecycleCameraController,
            onPhotoTaken: (Bitmap) -> Unit,
        ) {
            controller.takePicture(
                ContextCompat.getMainExecutor(applicationContext),
                object : ImageCapture.OnImageCapturedCallback() {
                    override fun onCaptureSuccess(image: ImageProxy) {
                        //Rotates the bitmap to the right orientation, than handles it
                        onPhotoTaken(rotateBitmap(image))
                    }

                    override fun onError(exception: ImageCaptureException) {
                        super.onError(exception)
                        Log.e("Camera", "Couldn't take photo:", exception)
                    }
                }
            )
        }

        private fun rotateBitmap(image:ImageProxy): Bitmap{
            val matrix = Matrix().apply {
                postRotate(image.imageInfo.rotationDegrees.toFloat())
            }
            return Bitmap.createBitmap(
                image.toBitmap(),
                0,
                0,
                image.width,
                image.height,
                matrix,
                true
            )
        }
    }
}
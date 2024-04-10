package hu.blueberry.projectaquamarine

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import hu.blueberry.camera.models.photo.InternalStoragePhoto
import hu.blueberry.camera.ui.TakePhotoView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception

class MainActivity : ComponentActivity() {

    companion object{
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
        )
    }

    private fun hasRequiredPermissions(): Boolean{
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it,
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO NOT REAL PERMISSION HANDLING fix ()
        if (!hasRequiredPermissions()){
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }

        setContent {
            TakePhotoView(applicationContext = applicationContext)
        }
    }

    fun savePhotoToInternalStorage(filename:String, bitmap: Bitmap): Boolean{
        return try {
            openFileOutput("$filename.jpg", MODE_PRIVATE).use { stream->
                if(!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)){
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException){
            e.printStackTrace()
            return false
        }
    }

    suspend fun loadPhotosFromInternalStorage(): List<InternalStoragePhoto>{
        return withContext(Dispatchers.IO){
            val files = filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
                val bytes = it.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
                InternalStoragePhoto(it.name, bitmap)
            } ?: listOf()
        }
    }


    private fun deletePhotoFromInternalStorage(filename: String):Boolean{
        return try {
            deleteFile(filename)
        } catch (e:Exception){
            e.printStackTrace()
            false
        }
    }
   
}


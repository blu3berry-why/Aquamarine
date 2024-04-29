package hu.blueberry.camera.services

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import com.zipp.ZipManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.blueberry.camera.models.photo.InternalStoragePhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject



class FileManager @Inject constructor(
    private var context: Context
) {


    /**
     * @param filename: The name of the file which you wanna save
     * @param bitmap: The image you wanna save
     * @return If the save was successful
     */
    fun savePhotoToInternalStorage( filename: String, bitmap: Bitmap): Boolean{
        return try {
            context.openFileOutput("$filename.jpg", ComponentActivity.MODE_PRIVATE).use { stream->
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
            val files = context.filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
                val bytes = it.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
                InternalStoragePhoto(it.name, bitmap)
            } ?: listOf()
        }
    }


    fun deletePhotoFromInternalStorage(filename: String):Boolean{
        return try {
            context.deleteFile(filename)
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }

    fun zipFiles(files: Array<String>, zipFileName: String?){
        ZipManager().zip(files, zipFileName)
    }


    fun unzipFiles(zipFile: String?, targetLocation: String){
        ZipManager().unzip(zipFile, targetLocation)
    }


}
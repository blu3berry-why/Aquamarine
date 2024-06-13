package hu.blueberry.drive.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.ComponentActivity
import hu.blueberry.drive.model.InternalStoragePhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject


class FileService @Inject constructor(
    private var context: Context,
) {

    /**
     * @param filename: The name of the file which you wanna save
     * @param bitmap: The image you wanna save
     * @return If the save was successful
     */
    fun savePhotoToInternalStorage( filename: String, bitmap: Bitmap): Boolean{
        return try {
            context.openFileOutput("$filename.png", ComponentActivity.MODE_PRIVATE).use { stream->
                if(!bitmap.compress(Bitmap.CompressFormat.PNG, 95, stream)){
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
        return withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }?.map {
                val bytes = it.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
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

    fun createPhotoFilePath(filename: String, extension: String = ".png"): File{
        deletePhotoFromInternalStorage(filename)
        return createFilePathFromFilename(filename, extension)
    }

    @Throws(IOException::class)
    fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while ((source.read(buf).also { length = it }) != -1) {
            target.write(buf, 0, length)
        }
    }


    fun fileInputStreamFromUri(uri: Uri, filename: String): Boolean{
        val inputStream = context.contentResolver.openInputStream(uri)
        return try {
            context.openFileOutput("$filename.png", ComponentActivity.MODE_PRIVATE).use { stream->
                copy(inputStream!!, stream)
            }
            true
        } catch (e: IOException){
            e.printStackTrace()
            return false
        }
    }

    fun zipFiles(files: Array<String>, zipFileName: String?){
        ZipService().zip(files, zipFileName)
    }


    fun unzipFiles(zipFile: String?, targetLocation: String){
        ZipService().unzip(zipFile, targetLocation)
    }


    fun createFilePathFromFilename(filename: String, extension:String): File{
        return File(context.filesDir, filename+extension)
    }
}
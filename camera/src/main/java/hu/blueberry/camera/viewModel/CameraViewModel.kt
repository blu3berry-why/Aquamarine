package hu.blueberry.camera.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import hu.blueberry.camera.models.enums.PhotoClockType
import hu.blueberry.camera.models.enums.PhotoTakenTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import java.util.Date
import java.util.Locale

class CameraViewModel: ViewModel() {

    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()

    val selectedClockType = MutableStateFlow<PhotoClockType>(PhotoClockType.FNT_COLD)

    val selectedTakenType = MutableStateFlow<PhotoTakenTime>(PhotoTakenTime.OPENING)

    // TODO Is there an other way of auto updating it?
    val photoName = MutableStateFlow<String>(getPhotoName())


    fun onTakePhoto(bitmap:Bitmap){
        _bitmaps.value += bitmap
    }

    /**
     * @param filename: The name of the file which you wanna save
     * @param bitmap: The image you wanna save
     * @return If the save was successful
     */
    fun savePhotoToInternalStorage(context: Context, filename: String, bitmap: Bitmap): Boolean{
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


    fun getPhotoName(): String {
        val currentTime: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedDate: String = df.format(currentTime)


        return "${formattedDate}_${selectedClockType.value.textForm}_${selectedTakenType.value.textForm}"
    }

    fun setPhotoName(){
        photoName.value = getPhotoName()
    }



}
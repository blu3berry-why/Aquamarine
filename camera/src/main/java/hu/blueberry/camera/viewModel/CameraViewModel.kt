package hu.blueberry.camera.viewModel

import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.camera.models.enums.PhotoClockType
import hu.blueberry.camera.models.enums.PhotoTakenTime
import hu.blueberry.camera.services.FileManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor(
    private val fileManager: FileManager
): ViewModel() {

    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()

    val selectedClockType = MutableStateFlow<PhotoClockType>(PhotoClockType.FNT_COLD)

    val selectedTakenType = MutableStateFlow<PhotoTakenTime>(PhotoTakenTime.OPENING)

    val nameOfTheEvent = MutableStateFlow<String>("")

    // TODO Is there an other way of auto updating it?
    val photoName = MutableStateFlow<String>(getPhotoName())


    fun onTakePhoto(bitmap:Bitmap){
        _bitmaps.value += bitmap
    }

    fun savePhotoToInternalStorage(filename: String, bitmap: Bitmap): Boolean {
        return fileManager.savePhotoToInternalStorage(filename, bitmap)
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
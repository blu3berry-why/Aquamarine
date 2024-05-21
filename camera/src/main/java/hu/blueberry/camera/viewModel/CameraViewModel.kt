package hu.blueberry.camera.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.camera.models.enums.PhotoClockType
import hu.blueberry.camera.models.enums.PhotoTakenTime
import hu.blueberry.drive.services.FileService
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.base.StringValues
import hu.blueberry.drive.base.handleResponse
import hu.blueberry.drive.permissions.PermissionRequestManager
import hu.blueberry.drive.repositories.DriveRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor(
    private val fileService: FileService,
    private val driveRepository: DriveRepository,
) : ViewModel(), PermissionHandlingViewModel {
    companion object {
        const val TAG = "CameraViewModel"
    }
    override val permissionManager: PermissionRequestManager = PermissionRequestManager()

    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap = _bitmap.asStateFlow()

    val selectedClockType = MutableStateFlow<PhotoClockType>(PhotoClockType.FNT_COLD)

    val selectedTakenType = MutableStateFlow<PhotoTakenTime>(PhotoTakenTime.OPENING)

    val nameOfTheEvent = MutableStateFlow<String>("")

    // TODO Is there an other way of auto updating it?
    val photoName = MutableStateFlow<String>(getPhotoName())


    fun onTakePhoto(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }

    fun createDriveFolder() {
        viewModelScope.launch {
            driveRepository.upsertFolder(StringValues.BASE_FOLDER_NAME).collectLatest {
                handleResponse(it,
                    onSuccess = {
                        Log.d(TAG, it)
                    },
                    onError = { error ->
                        requestPremission(exception = error){
                            createDriveFolder()
                        }
                    })
            }
        }
    }

    fun uploadPNG(){
        viewModelScope.launch {
            driveRepository.createImageToDrive(photoName.value, bitmap.value!!).collectLatest {
                handleResponse(it,
                    onSuccess = {
                        fileService.deletePhotoFromInternalStorage(filename = photoName.value+".png")
                    },
                    onError = { error ->
                        Log.d(TAG, error.toString())
                        requestPremission(error){
                            uploadPNG()
                        }
                    })
            }
        }
    }


    private fun savePhotoToInternalStorage(filename: String, bitmap: Bitmap): Boolean {
        return fileService.savePhotoToInternalStorage(filename, bitmap)
    }


    private fun getPhotoName(): String {
        val currentTime: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate: String = df.format(currentTime)

        return "${formattedDate}_${selectedClockType.value.textForm}_${selectedTakenType.value.textForm}"
    }

    fun setPhotoName() {
        photoName.value = getPhotoName()
    }

    fun saveImage(context: Context){
        val success = savePhotoToInternalStorage(
            filename = getPhotoName(),
            bitmap = bitmap.value!!
        )
        if (success) {
            Toast.makeText(
                context,
                "Image: ${getPhotoName()} has been saved.",
                Toast.LENGTH_LONG
            ).show()
        }
    }


}
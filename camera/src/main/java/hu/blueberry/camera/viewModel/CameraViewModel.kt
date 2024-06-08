package hu.blueberry.camera.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
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
import hu.blueberry.drive.model.MemoryDatabase
import hu.blueberry.drive.permissions.PermissionRequestManager
import hu.blueberry.drive.repositories.DriveRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date
import java.util.Locale
import java.util.Objects
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor(
    private val fileService: FileService,
    private val driveRepository: DriveRepository,
    private val memoryDatabase: MemoryDatabase,
) : PermissionHandlingViewModel() {
    companion object {
        const val TAG = "CameraViewModel"
    }

    init {
        setFolderName()
    }

    private val _bitmap = MutableStateFlow<Bitmap?>(null)
    val bitmap = _bitmap.asStateFlow()

    val selectedClockType = MutableStateFlow<PhotoClockType>(PhotoClockType.FNT_COLD)

    val selectedTakenType = MutableStateFlow<PhotoTakenTime>(PhotoTakenTime.OPENING)

    val nameOfTheEvent = MutableStateFlow<String>("")

    // TODO Is there an other way of auto updating it?
    val photoName = MutableStateFlow<String>(getPhotoName())

    var filePath: File? = null

    var _selectedImageUri: MutableStateFlow<Uri?> = MutableStateFlow<Uri?>(null)

    val selectedImageUri = _selectedImageUri.asStateFlow()

    var uri: Uri? = null



    fun createDriveFolder() {
        handleUserRecoverableAuthError(
            request = { driveRepository.upsertFolder(StringValues.BASE_FOLDER_NAME) },
            onSuccess = {
                Log.d(TAG, it)
            }
        )
    }

    fun uploadPNG(onSuccess: () -> Unit = {}, onError: (Any?) -> Unit = {}) {
        val list = memoryDatabase.folderId?.let { listOf(it) } ?: listOf()
        handleUserRecoverableAuthError(
            request = { driveRepository.createImageToDrive(photoName.value, filePath!!, list) },
            onSuccess = {
                fileService.deletePhotoFromInternalStorage(filename = photoName.value + ".png")
                onSuccess.invoke()
            },
            onError = onError
        )
    }

    fun createImageFile(context: Context):File{
        return context.createImageFile(photoName.value)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun setImageUri(uri: Uri){
        _selectedImageUri.value = uri
        fileService.fileInputStreamFronUri(uri, filename = photoName.value)
        filePath = fileService.createFilePathFromFilename(photoName.value, ".png")
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

    fun showToastImageHasBeenSaved(context: Context){
        viewModelScope.launch {
            Toast.makeText(
                context,
                "Image: ${getPhotoName()} has been saved.",
                Toast.LENGTH_LONG
            ).show()
        }

    }


    private fun setFolderName() {
        handleUserRecoverableAuthError(
            request = { driveRepository.searchFolderFlow(StringValues.BASE_FOLDER_NAME) },
            onSuccess = { id ->
                memoryDatabase.folderId = id
            },
        )
    }
}

fun Context.createImageFile(name:String): File {
    // Create an image file name
    val image = File.createTempFile(
        name, /* prefix */
        ".png", /* suffix */
        externalCacheDir      /* directory */
    )
    return image
}
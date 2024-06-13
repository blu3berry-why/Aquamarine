package hu.blueberry.camera.viewModel

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.camera.models.enums.PhotoClockType
import hu.blueberry.camera.models.enums.PhotoTakenTime
import hu.blueberry.drive.services.FileService
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.base.StringValues
import hu.blueberry.drive.model.MemoryDatabase
import hu.blueberry.drive.repositories.DriveRepository
import hu.blueberry.drive.services.createImageFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date
import java.util.Locale
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

    val selectedClockType = MutableStateFlow<PhotoClockType>(PhotoClockType.FNT_COLD)

    val selectedTakenType = MutableStateFlow<PhotoTakenTime>(PhotoTakenTime.OPENING)

    val nameOfTheEvent = MutableStateFlow<String>("")

    // TODO Is there an other way of auto updating it?
    val photoName = MutableStateFlow<String>(getPhotoName())

    private var filePath: File? = null

    private var _selectedImageUri: MutableStateFlow<Uri?> = MutableStateFlow<Uri?>(null)

    val selectedImageUri = _selectedImageUri.asStateFlow()

    var uri: Uri? = null

    /**
     * Sets the variable for the photo's conventional name
     */
    fun setPhotoName() {
        photoName.value = getPhotoName()
    }

    /**
     * Creates a photo name from the convention: *DATE_CLOCKTYPE_TAKENTIME*
     * @return a String with the name, **WITHOUT** the extension (.png, .jpg...)
     */
    private fun getPhotoName(): String {
        val currentTime: Date = Calendar.getInstance().time
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate: String = df.format(currentTime)

        return "${formattedDate}_${selectedClockType.value.textForm}_${selectedTakenType.value.textForm}"
    }

    /**
     * Creates a temporary file from the uri, and sets the *selectedImageUri* and the *filePath* for that temporary image.
     * @param uri The uri of the file which is needed to be cached and selected in the view model
     */
    fun createTempImageAndSetFilePathAndSelectedUri(uri: Uri){
        _selectedImageUri.value = uri
        fileService.fileInputStreamFromUri(uri, filename = photoName.value)
        filePath = fileService.createFilePathFromFilename(photoName.value, ".png")
    }

    /**
     * Creates a temporary image File
     * @param context The local context of the application
     * @return A File where the photo was created, for the camera to use it to write the photo it has taken.
     */
    fun createImageFile(context: Context):File{
        return context.createImageFile(photoName.value)
    }

    /**
     *
     */
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

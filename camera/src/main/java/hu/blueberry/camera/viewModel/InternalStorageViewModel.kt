package hu.blueberry.camera.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.model.InternalStoragePhoto
import hu.blueberry.drive.model.MemoryDatabase
import hu.blueberry.drive.repositories.DriveRepository
import hu.blueberry.drive.services.FileService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject
import kotlin.js.ExperimentalJsFileName


@HiltViewModel
class InternalStorageViewModel @Inject constructor(
    private val fileService: FileService,
    private val driveRepository: DriveRepository,
    private val memoryDatabase: MemoryDatabase,
) : PermissionHandlingViewModel() {

    private val _internalStoragePhotos: MutableStateFlow<MutableList<InternalStoragePhoto>> =
        MutableStateFlow<MutableList<InternalStoragePhoto>>(mutableListOf())

    val internalStoragePhotos = _internalStoragePhotos.asStateFlow()

    private val _filteredInternalStoragePhotos =
        MutableStateFlow<MutableList<InternalStoragePhoto>>(mutableListOf())

    val filteredInternalStoragePhotos = _filteredInternalStoragePhotos.asStateFlow()

    init {
        loadInternalStoragePhotos()
    }

    val filterText = MutableStateFlow<String>("")

    private fun loadInternalStoragePhotos() {
        viewModelScope.launch {
            _internalStoragePhotos.value =
                fileService.loadPhotosFromInternalStorage().toMutableList()
            _filteredInternalStoragePhotos.value = _internalStoragePhotos.value
        }
    }

    fun filterInternalStoragePhotos() {
        _filteredInternalStoragePhotos.value =
            _internalStoragePhotos.value.filter{photo -> photo.name.contains(filterText.value) }.toMutableList()
    }

    fun uploadPNG(
        internalStoragePhoto: InternalStoragePhoto,
        onSuccess: () -> Unit = {},
        onError: (Any?) -> Unit = {}
    ) {
        val parentList = memoryDatabase.folderId?.let { listOf(it) } ?: listOf()
        val path = File(internalStoragePhoto.path)
        handleUserRecoverableAuthError(
            request = {
                driveRepository.createImageToDrive(
                    internalStoragePhoto.name,
                    path,
                    parentList
                )
            },
            onSuccess = {
                removePhoto(internalStoragePhoto)
                fileService.deletePhotoFromInternalStorage(filename = internalStoragePhoto.name)
                onSuccess.invoke()
            },
            onError = onError
        )
    }

    private fun removePhoto(internalStoragePhoto: InternalStoragePhoto) {
        _internalStoragePhotos.value.remove(internalStoragePhoto)
        filterInternalStoragePhotos()
    }
}
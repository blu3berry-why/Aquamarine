package hu.blueberry.camera.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
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

    val internalStoragePhotos :SnapshotStateList<InternalStoragePhoto> = mutableStateListOf()
    val filteredInternalStoragePhotos :SnapshotStateList<InternalStoragePhoto> = mutableStateListOf()


    init {
        loadInternalStoragePhotos()
    }

    val filterText = MutableStateFlow<String>("")

    private fun loadInternalStoragePhotos() {
        viewModelScope.launch {
            internalStoragePhotos.clear()
            internalStoragePhotos.addAll(fileService.loadPhotosFromInternalStorage())
            filterInternalStoragePhotos()
        }
    }

    fun filterInternalStoragePhotos() {
        filteredInternalStoragePhotos.clear()
        val filteredPhotos = internalStoragePhotos.filter{photo -> photo.name.contains(filterText.value) }
        filteredInternalStoragePhotos.addAll(filteredPhotos)
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
        internalStoragePhotos.remove(internalStoragePhoto)
        filterInternalStoragePhotos()
    }
}
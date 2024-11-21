package hu.blueberry.projectaquamarine.features.filepicker

import androidx.lifecycle.viewModelScope
import com.google.api.services.drive.model.File
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.base.handleResponse
import hu.blueberry.drive.model.MemoryDatabase
import hu.blueberry.drive.repositories.DriveRepository
import hu.blueberry.drive.services.DriveService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilePickerViewModel @Inject constructor(
    private val driveRepository: DriveRepository,
    private val memoryDatabase: MemoryDatabase
) : PermissionHandlingViewModel(){
    /**
     * Starts with the root folder of the Drive
     */
    private var parentList: MutableList<File>
        = mutableListOf(File().apply {
            id = "root"
            name = "Root"
        })

    private val lastParent: String?
        /**
         * @returns The last parent (the current folder's id) or null
         */
        get() = parentList.lastOrNull()?.id

    /**
     * Here we only work with folders
     */
    private var mimeType: String? = DriveService.MimeType.FOLDER

    /**
     * The folders to show
     */
    private var _files: MutableStateFlow<List<File>>  = MutableStateFlow(listOf())
    val displayedFiles = _files.asStateFlow()

    /**
     * Loads the files in the root folder
     */
    init {
        loadFilesInFolder()
    }

    /**
     * Loads the files with the mime type (Folder) that are in the given parent folder
     */
    private fun loadFilesInFolder(){
        val list = lastParent?.let { listOf(it) } ?: listOf()

        viewModelScope.launch {
            driveRepository.searchFilesMatchingParentsAndMimeType(list, mimeType).collectLatest {
                handleResponse(
                    resource = it,
                    onSuccess = {returnedFiles -> _files.value = returnedFiles}
                    )
            }
        }
    }

    fun openFolder(folder: File){
        parentList.add(folder)
        loadFilesInFolder()
    }

    fun selectFolder(){
        lastParent?.let { memoryDatabase.folderId = it  }
    }
}
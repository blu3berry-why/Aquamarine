package hu.blueberry.projectaquamarine.features.filepicker

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import com.google.api.services.drive.model.File
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.base.handleResponse
import hu.blueberry.drive.model.MemoryDatabase
import hu.blueberry.drive.repositories.DriveRepository
import hu.blueberry.drive.services.DriveService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderPickerViewModel @Inject constructor(
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
    private var mimeTypes: List<String>? = listOf( DriveService.MimeType.FOLDER, DriveService.MimeType.SPREADSHEET)

    /**
     * The folders to show
     */
    /*private var _files: MutableStateFlow<List<File>>  = MutableStateFlow(listOf())
    val displayedFiles = _files.asStateFlow()*/


    private val _files: SnapshotStateList<File> = mutableStateListOf()
    val displayedFiles = _files

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
            driveRepository.searchFilesMatchingParentsAndMimeTypes(list, mimeTypes).collectLatest {
                handleResponse(
                    resource = it,
                    onSuccess = {returnedFiles ->
                        setFilesAndFolders(returnedFiles)
                        }
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

    private fun setFilesAndFolders(filesAndFolders:List<File>){

        _files.clear()
        val sortedFiles = filesAndFolders.sortedBy {
            it.mimeType
        }
        _files.addAll(sortedFiles)
    }
}
package hu.blueberry.projectaquamarine.features.filepicker

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.api.services.drive.model.File
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.repository.WorkingDirectoryRepository
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.repositories.DriveRepository
import hu.blueberry.drive.services.DriveService
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.model.updatedextradata.MyFile
import hu.blueberry.persistentstorage.model.updatedextradata.WorkingDirectory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FilePickerViewModel @Inject constructor(
    private val driveRepository: DriveRepository,
    private val workingDirectoryRepository: WorkingDirectoryRepository
) : PermissionHandlingViewModel() {
    /**
     * Starts with the root folder of the Drive
     */
    private var parentList: MutableList<File> = mutableListOf(File().apply {
        id = "root"
        mimeType = DriveService.MimeType.FOLDER
        name = "Root"
    })

    private val lastParent: File?
        /**
         * @returns The last parent (the current folder's id) or null
         */
        get() = parentList.lastOrNull()

    private val lastParentName: MutableStateFlow<String> = MutableStateFlow("Root")
    val lastParentDisplayName: StateFlow<String> = lastParentName.asStateFlow()


    private var mimeTypes: List<String>? =
        listOf(DriveService.MimeType.FOLDER, DriveService.MimeType.SPREADSHEET)
    val acceptedMimeTypes: List<String>?
        get() = mimeTypes

    var chooseType: MutableStateFlow<String> = MutableStateFlow(DriveService.MimeType.FOLDER)

    private lateinit var workingDirectory: WorkingDirectory

    /**
     * The folders to show
     *//*private var _files: MutableStateFlow<List<File>>  = MutableStateFlow(listOf())
    val displayedFiles = _files.asStateFlow()*/


    private val _files: SnapshotStateList<File> = mutableStateListOf()
    val displayedFiles = _files

    /**
     * Loads the files in the root folder
     */
    init {
        loadFilesInFolder()
        loadWorkingDirectory()
    }

    /**
     * Loads the files with the mime type (Folder) that are in the given parent folder
     */
    private fun loadFilesInFolder() {
        val list = lastParent?.let { listOf(it.id) } ?: listOf()

        handleUserRecoverableAuthError(request = {
            driveRepository.searchFilesMatchingParentsAndMimeTypes(
                parentsList = list,
                mimeTypes = mimeTypes
            )
        },
            onSuccess = { returnedFiles -> setFilesAndFolders(returnedFiles) })

    }

    private fun loadWorkingDirectory() {
        runIO(request = {
            workingDirectory = workingDirectoryRepository.getWorkingDirectory()
        })
    }

    fun openFolder(folder: File) {
        parentList.add(folder)
        lastParentName.value = folder.name
        loadFilesInFolder()
    }

    fun selectFolder() {
        lastParent?.let {
            runIO(
                request = { workingDirectoryRepository.setWorkingDirectoryFolder(workingDirectory, it) }
            )
        }

    }

    fun selectSpreadSheet(file: File) {
        runIO(
            request = { workingDirectoryRepository.setWorkingDirectorySpreadsheet(workingDirectory, file) }
        )
    }

    private fun setFilesAndFolders(filesAndFolders: List<File>) {

        _files.clear()
        val sortedFiles = filesAndFolders.sortedBy {
            it.mimeType
        }
        _files.addAll(sortedFiles)
    }

    fun setMimeTypes(mimeTypesList: List<String>) {
        mimeTypes = mimeTypesList
    }


}
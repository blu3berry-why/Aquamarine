package hu.blueberry.drive.repositories

import android.graphics.Bitmap
import hu.blueberry.drive.base.ResourceState
import hu.blueberry.drive.base.handleWithFlow
import hu.blueberry.drive.model.FileInfo
import hu.blueberry.drive.services.DriveService
import hu.blueberry.drive.services.FileService
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class DriveRepository @Inject constructor(
    private var driveManager: DriveService,
    private val fileService: FileService,
) {



    suspend fun createFolder(name: String): Flow<ResourceState<String>> {
        return handleWithFlow { driveManager.createFolder(name) }
    }

    suspend fun createFolderFlow(name: String): Flow<ResourceState<String>> {
        return handleWithFlow { driveManager.createFolder(name) }
    }

    suspend fun createSpreadSheetInFolderFlow(
        folderId: String,
        sheetName: String
    ): Flow<ResourceState<String>> {
        return handleWithFlow { driveManager.createSpreadSheetInFolder(folderId, sheetName) }
    }

    suspend fun createFileFlow(
        fileName: String,
        parents: List<String>,
        mimeType: String,
        file: File
    ): Flow<ResourceState<String>> {
        return handleWithFlow { driveManager.createFile(fileName, parents, mimeType, file) }
    }

    suspend fun createImageToDrive(fileName: String, bitmap: Bitmap, parents: List<String> = listOf()): Flow<ResourceState<String>> {
        val success = fileService.savePhotoToInternalStorage(fileName, bitmap)
        if (success){
            return handleWithFlow { driveManager.createFile(fileName, parents, mimeType = DriveService.MimeType.PNG,
                fileService.createFilePathFromFilename(fileName, ".png")) }
        }else{
            throw Exception("Image can't be created, maybe there is an image with the same name...")
        }

    }

    suspend fun createImageToDrive(fileName: String, filePath: File, parents: List<String> = listOf()): Flow<ResourceState<String>> {
        return handleWithFlow { driveManager.createFile(fileName, parents, mimeType = DriveService.MimeType.PNG, filePath) }
    }


    suspend fun searchSingleFolderMatchingStringInName(name: String): Flow<ResourceState<String?>> {
        return handleWithFlow { driveManager.searchSingleFolderMatchingStringInName(name) }
    }

    suspend fun searchFilesMatchingParentsAndMimeTypes(parentsList: List<String>?, mimeTypes: List<String>?): Flow<ResourceState<MutableList<com.google.api.services.drive.model.File>>> {
        return handleWithFlow { driveManager.searchFilesMatchingParentsAndMimeTypes(parentsList, mimeTypes) }
    }

    suspend fun createFolderBlocking(name: String): String {
        return driveManager.createFolder(name)
    }

    suspend fun searchFolderBlocking(name: String): String? {
        return driveManager.searchSingleFolderMatchingStringInName(name)
    }

    suspend fun createSpreadSheetInFolderBlocking(
        folderId: String,
        sheetName: String
    ): String {
        return driveManager.createSpreadSheetInFolder(folderId, sheetName)
    }

    suspend fun createFileFlowBlocking(
        fileName: String,
        parents: List<String>,
        mimeType: String,
        file: File
    ): String {
        return driveManager.createFile(fileName, parents, mimeType, file)
    }


    private fun _getAllSpreadSheetsInFolder(
        parentsList: List<String>,
        mimeType: String = DriveService.MimeType.SPREADSHEET
    ): List<FileInfo> {

        val fileList = driveManager.searchFilesMatchingParentsAndMimeTypes(parentsList, listOf( mimeType))

        return fileList.map { FileInfo(name = it.name, id = it.id) }
    }

    suspend fun getAllSpreadSheetsInFolder(
        parentsList: List<String>,
        mimeType: String = DriveService.MimeType.SPREADSHEET
    ): Flow<ResourceState<List<FileInfo>>> {
        return handleWithFlow {
            _getAllSpreadSheetsInFolder(parentsList, mimeType)
        }
    }


    suspend fun upsertFolder(folderName: String): Flow<ResourceState<String>> {
        return handleWithFlow {
            searchFolderBlocking(folderName)
                ?: createFolderBlocking(folderName)
        }
    }

    suspend fun searchSpreadSheet(spreadSheetName: String): Flow<ResourceState<List<FileInfo>>> {
        return handleWithFlow {
            _searchSpreadSheet(spreadSheetName)
        }
    }

    private suspend fun _searchSpreadSheet(spreadSheetName: String): List<FileInfo> {
        val spreadSheets = driveManager.searchFilesWithStringMatchingInName(spreadSheetName)
        return spreadSheets.map {  FileInfo(name = it.name, id = it.id)}
    }


}
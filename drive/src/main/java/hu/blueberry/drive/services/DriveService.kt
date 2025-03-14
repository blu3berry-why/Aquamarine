package hu.blueberry.drive.services

import android.util.Log
import com.google.api.client.http.FileContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.drive.model.File
import hu.blueberry.drive.base.CloudBase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DriveService @Inject constructor(
    private var cloudBase: CloudBase
) {

    var scopes = listOf(DriveScopes.DRIVE_FILE, DriveScopes.DRIVE_METADATA)

    object MimeType {
        const val FOLDER = "application/vnd.google-apps.folder"
        const val SPREADSHEET = "application/vnd.google-apps.spreadsheet"
        const val XLSL_DOCUMENT =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        const val PDF = "application/pdf"
        const val PNG = "image/png"
    }

    companion object {
        const val TAG = "DriveService"
    }

    val drive: Drive
        get() = getDriveService()!!


    private fun getDriveService(): Drive? {
        return Drive.Builder(
            NetHttpTransport(), GsonFactory.getDefaultInstance(),
            cloudBase.getCredentials(scopes)
        ).setApplicationName("Project Aquamarine").build()
    }


    fun createFolder(name: String): String {

        val folder: File

        val folderData = File().apply {
            this.name = name
            this.mimeType = MimeType.FOLDER
        }

        folder = drive.files().create(folderData).execute()

        return folder.id
    }


    fun searchSingleFolderMatchingStringInName(name: String): String? {

        val folderId: String?

        val files = drive.files().list()

        files.q = GoogleDriveQueryBuilder()
            .mimeType(MimeType.FOLDER)
            .createQuery()

        val result = files.execute()

        result.files.forEach {
            Log.d(TAG, it.id ?: "no id")
        }

        val folder = result.files.filter {
            it.name == name
        }.firstOrNull()

        folderId = folder?.id

        return folderId

    }


    fun searchFilesMatchingParentsAndMimeTypes(
        parentIdList: List<String>?,
        mimeTypes: List<String>?
    ): MutableList<File> {
        val files = drive.files().list()
        files.q = GoogleDriveQueryBuilder()
            .parents(parentIdList)
            .and {
                it.listOfMimeTypes(mimeTypes)
            }
            .createQuery()

        val result = files.execute()

        result.files.forEach {
            Log.d(TAG, "File(name = ${it.name}, id = ${it.id})")
        }

        return result?.files ?: mutableListOf()
    }

    fun searchFilesWithStringMatchingInName(name: String): MutableList<File> {

        val files = drive.files().list()

        //files.q = "mimeType='${MimeType.SPREADSHEET}'" + " and name contains '$name'"
        files.q = GoogleDriveQueryBuilder()
            .mimeType(MimeType.SPREADSHEET)
            .and {
                it.queryText("name")
                    .contains()
                    .stringValue(name)
            }
            .createQuery()

        val result = files.execute()

        result.files.forEach {
            Log.d(TAG, "File(name = ${it.name}, id = ${it.id})")
        }

        return result.files ?: mutableListOf()

    }

    fun createSpreadSheetInFolder(folderId: String, sheetName: String): String {
        val folderData = File().apply {
            this.name = sheetName
            this.mimeType = MimeType.SPREADSHEET
            this.parents = listOf(folderId)
        }

        val file = drive.files().create(folderData).execute()
        return file.id
    }


    fun createFile(
        name: String,
        parents: List<String>,
        mimeType: String,
        file: java.io.File
    ): String {

        var f = File().apply {
            this.name = name
            this.parents = parents
            this.mimeType = mimeType
        }

        val mediaContent = FileContent(MimeType.PDF, file)

        f = drive.files().create(f, mediaContent).execute()
        return f.id
    }


}
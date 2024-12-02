package hu.blueberry.drinks.repository

import com.google.api.services.drive.model.File
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.model.updatedextradata.MyFile
import hu.blueberry.persistentstorage.model.updatedextradata.WorkingDirectory
import javax.inject.Inject

class WorkingDirectoryRepository @Inject constructor(
    val database: Database
) {
    suspend fun getWorkingDirectory(): WorkingDirectory {
        val cached = database.workingDirectoryDao().getWorkingDirectoryInfo(WorkingDirectory.SavedDatabaseId)

        return cached ?: WorkingDirectory(WorkingDirectory.SavedDatabaseId, null, null)
    }

    suspend fun setWorkingDirectoryFolder(workingDirectory: WorkingDirectory, file: File){
        val myFile = MyFile.fromFile(file)
        database.workingDirectoryDao().upsertWorkingDirectory(workingDirectory.copy(workFolder = myFile))
    }


    suspend fun setWorkingDirectorySpreadsheet(workingDirectory: WorkingDirectory, file: File){
        val myFile = MyFile.fromFile(file)
        database.workingDirectoryDao().upsertWorkingDirectory(workingDirectory.copy(choosenSpreadSheet = myFile))
    }
}
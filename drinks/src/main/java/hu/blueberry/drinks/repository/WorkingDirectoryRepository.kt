package hu.blueberry.drinks.repository

import com.google.api.services.drive.model.File
import hu.blueberry.drinks.model.MemoryDatabase2
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.model.updatedextradata.MyFile
import hu.blueberry.persistentstorage.model.updatedextradata.WorkingDirectory
import javax.inject.Inject

class WorkingDirectoryRepository @Inject constructor(
    private val database: Database,
    private val memoryDatabase: MemoryDatabase2
) {

    suspend fun getWorkingDirectoryForDependencyInjection(): WorkingDirectory {
        return getWorkingDirectoryFromDatabase()
    }

    suspend fun getWorkingDirectory(): WorkingDirectory {
        memoryDatabase.workingDirectory = getWorkingDirectoryFromDatabase()
        return memoryDatabase.workingDirectory
    }

    private suspend fun getWorkingDirectoryFromDatabase(): WorkingDirectory {
        val cached = database.workingDirectoryDao().getWorkingDirectory(WorkingDirectory.SavedDatabaseId)

        return cached ?: WorkingDirectory(WorkingDirectory.SavedDatabaseId, null, null)
    }

    suspend fun setWorkingDirectoryFolder(workingDirectory: WorkingDirectory, file: File){
        val myFile = MyFile.fromFile(file)
        database.workingDirectoryDao().upsertWorkingDirectory(workingDirectory.copy(workFolder = myFile))
    }


    suspend fun setWorkingDirectorySpreadsheet(workingDirectory: WorkingDirectory, file: File){
        val myFile = MyFile.fromFile(file)
        memoryDatabase.workingDirectory = workingDirectory.copy(choosenSpreadSheet = myFile)
        database.workingDirectoryDao().upsertWorkingDirectory(memoryDatabase.workingDirectory)
    }
}
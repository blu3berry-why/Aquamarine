package hu.blueberry.persistentstorage.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import hu.blueberry.persistentstorage.model.updatedextradata.WorkingDirectory

@Dao
interface WorkingDirectoryDao {

    @Query("SELECT * FROM working_directories WHERE id = :id")
    fun getWorkingDirectoryInfo(id:Int): WorkingDirectory?

    @Upsert
    fun upsertWorkingDirectory(workingDirectory: WorkingDirectory)
}
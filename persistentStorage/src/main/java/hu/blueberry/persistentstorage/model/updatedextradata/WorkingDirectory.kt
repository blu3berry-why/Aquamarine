package hu.blueberry.persistentstorage.model.updatedextradata

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.api.services.drive.model.File


@Entity(tableName = "working_directories")
data class WorkingDirectory(
    @PrimaryKey val id: Int,
    @Embedded(prefix = "folder_")
    val workFolder: MyFile?,
    @Embedded(prefix = "spreadsheet_")
    val choosenSpreadSheet: MyFile?,
){
    companion object {
        val SavedDatabaseId = 5
    }
}

@Entity
data class MyFile(
    @ColumnInfo("id") val id: String,
    @ColumnInfo("mime_type")val mimeType: String,
    @ColumnInfo("name")val name: String,
)
{
    companion object{
        fun fromFile(file:File):MyFile{
            return MyFile(
                id = file.id,
                mimeType = file.mimeType,
                name = file.name,
            )
        }
    }
}

package hu.blueberry.projectaquamarine.features.stand2.StandBasicInformation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.Insert
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.repository.ProductPropertiesRepository
import hu.blueberry.drinks.repository.StandRepository
import hu.blueberry.drinks.repository.WorkingDirectoryRepository
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.model.Settings
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.model.updatedextradata.WorkingDirectory
import hu.blueberry.persistentstorage.model.updatedextradata.spreadsheet.WorksheetStorageInfo
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StandBasicInformationViewModel @Inject constructor(
   private val workingDirectoryRepository: WorkingDirectoryRepository,
   private val standRepository: StandRepository
) : PermissionHandlingViewModel(){

    private lateinit var workingDirectory : WorkingDirectory

    var storages: SnapshotStateList<WorksheetStorageInfo> = mutableStateListOf()

    init {
        refresh()
    }

    fun refresh(){
        runIO(
            request = {
                workingDirectory = workingDirectoryRepository.getWorkingDirectory()
                //Todo replace
                standRepository.readAllStorageSheets(spreadsheetId = workingDirectory.choosenSpreadSheet!!.id, Settings.StorageMarker)
                storages.clear()
                storages.addAll(standRepository.getWorksheetInfos(workingDirectory.choosenSpreadSheet!!.id))
            }
        )
    }

}
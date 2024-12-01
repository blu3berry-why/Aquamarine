package hu.blueberry.drinks.feature.stand

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.model.updatedextradata.WorkingDirectory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StandStartScreenViewModel @Inject constructor(
    val database: Database,
) : PermissionHandlingViewModel() {

    private var workingDirectory: MutableStateFlow<WorkingDirectory?> = MutableStateFlow(null)

    val currentWorkingDirectory: StateFlow<WorkingDirectory?>
        get() = workingDirectory.asStateFlow()

    init {
        getWorkingDirectoryFromDatabase()
    }

    private fun getWorkingDirectoryFromDatabase() {
        runIO(request = {
            workingDirectory.value = database.workingDirectoryDao().getWorkingDirectoryInfo(5)
        })
    }




}
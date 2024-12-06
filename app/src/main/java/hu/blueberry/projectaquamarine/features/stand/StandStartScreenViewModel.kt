package hu.blueberry.projectaquamarine.features.stand

import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.repository.WorkingDirectoryRepository
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.persistentstorage.model.updatedextradata.WorkingDirectory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StandStartScreenViewModel @Inject constructor(
    private val workingDirectoryRepository: WorkingDirectoryRepository
) : PermissionHandlingViewModel() {

    private var workingDirectory: MutableStateFlow<WorkingDirectory?> = MutableStateFlow(null)

    val currentWorkingDirectory: StateFlow<WorkingDirectory?>
        get() = workingDirectory.asStateFlow()

    init {
        getWorkingDirectoryFromDatabase()
    }

    fun getWorkingDirectoryFromDatabase() {
        runIO(request = {
            workingDirectory.value = workingDirectoryRepository.getWorkingDirectory()
        })
    }




}
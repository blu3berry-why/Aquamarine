package hu.blueberry.projectaquamarine.features.stand.storage.list

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.model.MemoryDatabase2
import hu.blueberry.drinks.repository.StandRepository
import hu.blueberry.drinks.repository.WorkingDirectoryRepository
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.persistentstorage.dao.ProductAndStand
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StorageListViewModel @Inject constructor(
    private val standRepository: StandRepository,
    private val workingDirectoryRepository: WorkingDirectoryRepository,
    private val memoryDatabase2: MemoryDatabase2
) : PermissionHandlingViewModel() {

    val productsAndStands: SnapshotStateList<ProductAndStand> = mutableStateListOf()

    fun refresh(workSheetName: String) {

        //TODO gány
        runIO {
            memoryDatabase2.selectedWorksheet = workSheetName

            if (memoryDatabase2.workingDirectory.choosenSpreadSheet == null) {
                workingDirectoryRepository.getWorkingDirectory()
            }

            memoryDatabase2.runCodeIfSpreadsheetIdNotNull {
                runIO {
                    addProducts(standRepository.getProductStandsForSpreadsheetWithWorksheetName(
                        it,
                        workSheetName
                    ))

                    standRepository.readStorageSheet(it, workSheetName)
                    val result = standRepository.getProductStandsForSpreadsheetWithWorksheetName(
                        it,
                        workSheetName
                    )
                    addProducts(result)
                }
            }

        }
    }

    private fun addProducts(products: List<ProductAndStand>){
        productsAndStands.clear()
        val result = products.filter { it.product != null }
        productsAndStands.addAll(result)


    }


}
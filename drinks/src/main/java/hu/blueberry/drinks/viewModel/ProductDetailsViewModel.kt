package hu.blueberry.drinks.viewModel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.repository.ProductPropertiesRepository
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.base.StringValues
import hu.blueberry.drive.base.handleResponse
import hu.blueberry.drive.base.handleWithFlow
import hu.blueberry.drive.model.MemoryDatabase
import hu.blueberry.drive.repositories.DriveRepository
import hu.blueberry.drive.repositories.GoogleSheetRepository
import hu.blueberry.persistentstorage.model.updatedextradata.ProductProperties
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productPropertiesRepository: ProductPropertiesRepository,
    private val memoryDatabase: MemoryDatabase,
    private val driveRepository: DriveRepository
): PermissionHandlingViewModel() {

    var productList : MutableList<ProductProperties> = mutableListOf()

    init {
        handleUserRecoverableAuthError(
            request = {
                driveRepository.searchSpreadSheet(StringValues.SPREADSHEET_NAME)
            },
            onSuccess = { files ->
                val first = files.firstOrNull()
                memoryDatabase.spreadsheetId = first!!.id
            }
        )
    }

    fun readProductDetails(workSheetName: String){
        handleUserRecoverableAuthError(
            request = {
                productPropertiesRepository.readProductDetailsFlow(memoryDatabase.spreadsheetId!!, workSheetName)
            },
            onSuccess = {
                data -> productList = data
            }
        )
    }

}
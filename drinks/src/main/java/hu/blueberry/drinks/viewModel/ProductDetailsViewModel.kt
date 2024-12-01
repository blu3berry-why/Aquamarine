package hu.blueberry.drinks.viewModel


import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.repository.ProductPropertiesRepository
import hu.blueberry.drinks.repository.ScaleRepository
import hu.blueberry.drinks.repository.StandRepository
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.base.StringValues
import hu.blueberry.drive.model.MemoryDatabase
import hu.blueberry.drive.repositories.DriveRepository
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.model.updatedextradata.product.ProductProperties
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val productPropertiesRepository: ProductPropertiesRepository,
    private val memoryDatabase: MemoryDatabase,
    private val driveRepository: DriveRepository,
    private val scaleRepository: ScaleRepository,
    private val standRepository: StandRepository,
    private val database: Database
): PermissionHandlingViewModel() {

    var productDatabaseList : MutableList<ProductProperties> = mutableListOf()
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
                data ->
                    productList = data
            }
        )
    }

    fun readScaleInfo(workSheetName: String){
        handleUserRecoverableAuthError(
            request = {
                scaleRepository.readScaleFlow(memoryDatabase.spreadsheetId!!, workSheetName)
            }
        )
    }

    fun readAllStock(){
        handleUserRecoverableAuthError(
            request = {standRepository.readAllStorageSheetsFlow(spreadsheetId = memoryDatabase.spreadsheetId!!, storageMarker = "-Raktár")}
        )
    }






}
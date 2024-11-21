package hu.blueberry.drinks.viewModel


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.helper.productListFromValueRangeTyped
import hu.blueberry.drinks.helper.Drinks
import hu.blueberry.persistentstorage.model.Product
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.base.StringValues
import hu.blueberry.drive.model.MemoryDatabase
import hu.blueberry.drive.model.google.MajorDimension
import hu.blueberry.drive.model.google.Range
import hu.blueberry.drive.model.google.ValueRangeBuilder
import hu.blueberry.drive.repositories.DriveRepository
import hu.blueberry.drive.repositories.GoogleSheetRepository
import hu.blueberry.persistentstorage.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val driveRepository: DriveRepository,
    private val spreadsheetRepository: GoogleSheetRepository,
    private val memoryDatabase: MemoryDatabase,
    private val database: Database,
) :  PermissionHandlingViewModel() {

    companion object {
        const val TAG = "ProductsViewModel"
    }

    private val _productList: SnapshotStateList<Product> = mutableStateListOf()
    val productList = _productList


    init {
        handleUserRecoverableAuthError(
            request = {
                refreshFromDatabase()
                driveRepository.searchSpreadSheet(StringValues.SPREADSHEET_NAME)
                      },
            onSuccess = { files ->
                val first = files.firstOrNull()
                memoryDatabase.spreadsheetId = first!!.id
            }
        )
    }




     fun readProducts(){
         handleUserRecoverableAuthError(
             request = { spreadsheetRepository.readSpreadSheet(memoryDatabase.spreadsheetId!!, "F17!A3:E100") },
             onSuccess = { data ->
                 data?.let {
                     viewModelScope.launch(Dispatchers.IO) {
                         // To really refresh the list
                         database.productDao().deleteAll()
                         _productList.clear()
                         _productList.addAll(productListFromValueRangeTyped(data))
                         saveToDatabase()
                         refreshFromDatabase()
                     }
                 }
             }
         )
     }

    fun saveProducts(onSuccess: () -> Unit){
        val drinks = Drinks(productList.toList()).toList("F17")
        val valueRangeBuilder = ValueRangeBuilder(MajorDimension.ROW, Range("F17", "B3", "E100"))
        valueRangeBuilder.addAll(drinks)
        val valueRange = valueRangeBuilder.build()

        handleUserRecoverableAuthError(
            request = { spreadsheetRepository.updateSpreadSheet(memoryDatabase.spreadsheetId!!, valueRange) },
            onSuccess = { onSuccess() }
        )
    }


    private suspend fun saveToDatabase(){
        database.productDao().insertAll(*productList.toTypedArray())
    }

    private fun refreshFromDatabase(){
        viewModelScope.launch(Dispatchers.IO) {
            //For the least time delay between the clear and the add
            val products = database.productDao().getAll()
            _productList.clear()
            _productList.addAll(products)
        }
    }
}
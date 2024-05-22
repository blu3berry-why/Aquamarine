package hu.blueberry.drinks.viewModel


import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.services.sheets.v4.model.ValueRange
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.helper.productListFromValueRangeTyped
import hu.blueberry.persistentstorage.model.Product
import hu.blueberry.persistentstorage.model.ProductType
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.base.StringValues
import hu.blueberry.drive.base.handleResponse
import hu.blueberry.drive.model.MemoryDatabase
import hu.blueberry.drive.permissions.PermissionRequestManager
import hu.blueberry.drive.repositories.DriveRepository
import hu.blueberry.drive.repositories.GoogleSheetRepository
import hu.blueberry.persistentstorage.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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

    init {
        handleUserRecoverableAuthError(
            request = { driveRepository.searchSpreadSheet(StringValues.SPREADSHEET_NAME) },
            onSuccess = { files ->
                val first = files.firstOrNull()
                memoryDatabase.spreadsheetId = first!!.id
                readProducts()
            }
        )
    }

    private val _productList: SnapshotStateList<Product> = mutableStateListOf()
    val productList = _productList

    fun addProduct() {
        _productList.add(Product(name = "New Product"))
    }


     private fun readProducts(){
         handleUserRecoverableAuthError(
             request = { spreadsheetRepository.readSpreadSheet(memoryDatabase.spreadsheetId!!, "F17!A3:E100") },
             onSuccess = { data ->
                 data?.let {
                     //TODO MAP
                     _productList.addAll(productListFromValueRangeTyped(it))
                     viewModelScope.launch(Dispatchers.IO) {
                         database.productDao().deleteAll()
                         saveToDatabase()
                     }
                 }
             }
         )
     }



    private suspend fun saveToDatabase(){
        database.productDao().insertAll(*productList.toTypedArray())
    }
}
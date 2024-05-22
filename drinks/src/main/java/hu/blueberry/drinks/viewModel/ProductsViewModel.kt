package hu.blueberry.drinks.viewModel


import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
    override val permissionManager: PermissionRequestManager,
) : ViewModel(), PermissionHandlingViewModel {

    companion object {
        const val TAG = "ProductsViewModel"
    }

    init {
        viewModelScope.launch {
            driveRepository.searchSpreadSheet(StringValues.SPREADSHEET_NAME).collectLatest {
                handleResponse(it, onSuccess = { files ->
                    val first = files.firstOrNull()
                    memoryDatabase.spreadsheetId = first!!.id
                    readProducts()
                },
                    onError = { error ->
                        Log.d(TAG, error.toString())
                    })
            }
        }
    }

    private val _productList: SnapshotStateList<Product> = mutableStateListOf()
    val productList = _productList

    fun addProduct() {
        _productList.add(Product(name = "New Product"))
    }

    fun readProducts(){
        viewModelScope.launch {
            spreadsheetRepository.readSpreadSheet(memoryDatabase.spreadsheetId!!, "F17!A3:E100").collectLatest {
                handleResponse(it,
                    onSuccess = { data ->
                        if (data != null){
                            for (row in data.values){
                                if( row is ArrayList<*>){
                                    var pType: ProductType = ProductType.OTHER
                                    for (r in row) {
                                        val list = r as List<String>
                                        if (ProductType.isProductType(r[0])){
                                            pType = ProductType.fromString(r[0])
                                        }else{
                                            _productList.add(
                                                Product.convertFromArray(list)
                                                    .apply { this.productType=pType }
                                            )
                                        }


                                    }
                                }
                            }
                        }
                        viewModelScope.launch(Dispatchers.IO) {
                            //saveToDatabase()
                        }

                    },
                    onError = { error ->
                        requestPremission(error){
                            readProducts()
                        }
                        Log.d(TAG, error.toString())
                    })
            }
        }
    }

    private suspend fun saveToDatabase(){
        database.productDao().insertAll(*productList.toTypedArray())
    }
}
package hu.blueberry.projectaquamarine.viewModel


import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drinks.model.Drinks
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.drive.model.MemoryDatabase
import hu.blueberry.drive.model.google.MajorDimension
import hu.blueberry.drive.model.google.Range
import hu.blueberry.drive.model.google.ValueRangeBuilder
import hu.blueberry.drive.repositories.GoogleSheetRepository
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StandPageViewModel @Inject constructor(
    private val database: Database,
    private val memoryDatabase: MemoryDatabase,
    private val googleSheetRepository: GoogleSheetRepository
) : PermissionHandlingViewModel()
{
    private val productList: MutableList<Product> = mutableListOf()

    val itemCount:Int
        get() = productList.size

    init {
        refreshFromDatabase()
    }

    fun saveProducts(onSuccess: () -> Unit){
        val drinks = Drinks(productList.toList()).toList("F17")
        val valueRangeBuilder = ValueRangeBuilder(MajorDimension.ROW, Range("F17", "B3", "E100"))
        valueRangeBuilder.addAll(drinks)
        val valueRange = valueRangeBuilder.build()

        handleUserRecoverableAuthError(
            request = { googleSheetRepository.updateSpreadSheet(memoryDatabase.spreadsheetId!!, valueRange) },
            onSuccess = { onSuccess() }
        )
    }

    private fun refreshFromDatabase(){
        productList.clear()
        viewModelScope.launch(Dispatchers.IO) {
            productList.addAll(database.productDao().getAll())
        }
    }
}
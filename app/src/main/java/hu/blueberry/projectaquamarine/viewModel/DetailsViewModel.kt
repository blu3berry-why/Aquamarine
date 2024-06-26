package hu.blueberry.projectaquamarine.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val database: Database,
) : ViewModel(){
    var _product: MutableStateFlow<Product?> = MutableStateFlow(null)
    var product = _product.asStateFlow()
    fun getProduct(name:String){
        viewModelScope.launch (Dispatchers.IO) {
            _product.value = database.productDao().getProductByName(name)
        }
    }
}
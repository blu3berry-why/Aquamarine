package hu.blueberry.projectaquamarine.viewModel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drive.PermissionHandlingViewModel
import hu.blueberry.persistentstorage.Database
import hu.blueberry.persistentstorage.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleItemStandViewModel @Inject constructor(
    private val database: Database
): PermissionHandlingViewModel() {
    private val _product:MutableStateFlow<Product?> = MutableStateFlow(null)
    val product = _product.asStateFlow()

    private val _botteCount = MutableStateFlow<Int>(0)
    val botteCount = _botteCount.asStateFlow()

    private val _cartonCount = MutableStateFlow<Int>(0)
    val cartonCount =_cartonCount.asStateFlow()

    private val _scaleValue = MutableStateFlow<Int>(0)
    val scaleValue = _scaleValue.asStateFlow()

    private val _sum = MutableStateFlow<Int>(0)
    val sum = _sum.asStateFlow()

    fun loadProductFromDatabase(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
        _product.value = database.productDao().getProductById(id)
        }
    }

    fun updateSum(){
    }

    fun addCarton(){
        _cartonCount.value += 1
        _sum.value += 6
    }

    fun removeCarton(){
        _cartonCount.value -= 1
        if (_cartonCount.value < 0){
            _cartonCount.value = 0
            return
        }
        _sum.value -= 6
    }

    fun addBottle(){
        _botteCount.value += 1
        _sum.value += 1
    }

    fun removeBottle(){
        _botteCount.value -= 1
        if(_botteCount.value < 0){
            _botteCount.value = 0
            return
        }
        _sum.value -= 1
    }

    fun addScale(){
        _sum.value += scaleValue.value
        _scaleValue.value = 0
    }

    fun setScaleValue(string: String){
        _scaleValue.value = string.toIntOrNull() ?: 0
    }

}
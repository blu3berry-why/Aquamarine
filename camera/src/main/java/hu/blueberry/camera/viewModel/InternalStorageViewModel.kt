package hu.blueberry.camera.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.camera.models.photo.InternalStoragePhoto
import hu.blueberry.camera.services.FileManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class InternalStorageViewModel @Inject constructor(
    private val fileManager: FileManager
): ViewModel() {

    private val _internalStoragePhotos = MutableStateFlow<List<InternalStoragePhoto>>(emptyList())
    val internalStoragePhotos = _internalStoragePhotos.asStateFlow()


    private val _filteredInternalStoragePhotos = MutableStateFlow<List<InternalStoragePhoto>>(emptyList())
    val filteredInternalStoragePhotos = _filteredInternalStoragePhotos.asStateFlow()

    val filterText = MutableStateFlow<String>("")

    suspend fun loadInternalStoragePhotos(){
        _internalStoragePhotos.value = fileManager.loadPhotosFromInternalStorage()
        _filteredInternalStoragePhotos.value = _internalStoragePhotos.value
    }

    fun filterInternalStoragePhotos(filter: (photo: InternalStoragePhoto) -> Boolean){
        _filteredInternalStoragePhotos.value = _internalStoragePhotos.value.filter(filter)
    }
}
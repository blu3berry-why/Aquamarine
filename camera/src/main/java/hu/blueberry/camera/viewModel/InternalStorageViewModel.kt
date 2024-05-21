package hu.blueberry.camera.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.drive.model.InternalStoragePhoto
import hu.blueberry.drive.services.FileService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class InternalStorageViewModel @Inject constructor(
    private val fileService: FileService
): ViewModel() {

    private val _internalStoragePhotos = MutableStateFlow<List<InternalStoragePhoto>>(emptyList())
    val internalStoragePhotos = _internalStoragePhotos.asStateFlow()


    private val _filteredInternalStoragePhotos = MutableStateFlow<List<InternalStoragePhoto>>(emptyList())
    val filteredInternalStoragePhotos = _filteredInternalStoragePhotos.asStateFlow()

    val filterText = MutableStateFlow<String>("")

    suspend fun loadInternalStoragePhotos(){
        _internalStoragePhotos.value = fileService.loadPhotosFromInternalStorage()
        _filteredInternalStoragePhotos.value = _internalStoragePhotos.value
    }

    fun filterInternalStoragePhotos(filter: (photo: InternalStoragePhoto) -> Boolean){
        _filteredInternalStoragePhotos.value = _internalStoragePhotos.value.filter(filter)
    }
}
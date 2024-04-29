package hu.blueberry.camera.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import hu.blueberry.camera.viewModel.CameraViewModel
import hu.blueberry.camera.viewModel.InternalStorageViewModel


@Composable
fun FilteredInternalStoragePhotos(
    viewModel: InternalStorageViewModel = hiltViewModel(),
){
    val filterText = viewModel.filterText.collectAsState()
    val filteredPhotos = viewModel.filteredInternalStoragePhotos.collectAsState()

    LaunchedEffect(false) {
        viewModel.loadInternalStoragePhotos()
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Red), contentAlignment = Alignment.TopCenter){
       Column {
            TextField(value = filterText.value, onValueChange = { filterText ->
                viewModel.filterText.value = filterText
                viewModel.filterInternalStoragePhotos { photo -> photo.name.contains(filterText) }
            })

            LazyColumn {
                items(viewModel.filteredInternalStoragePhotos.value.size){
                    PhotoNameRow(name = filteredPhotos.value[it].name)
                }
            }
        }
    }
}

@Composable
fun PhotoNameRow(name: String){
    Text(text = name)
}

@Preview
@Composable
fun FilteredInternalStoragePhotosPreview(){
    FilteredInternalStoragePhotos()
}

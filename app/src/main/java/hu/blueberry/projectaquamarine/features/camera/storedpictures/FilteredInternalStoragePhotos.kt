package hu.blueberry.projectaquamarine.features.camera.storedpictures

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun FilteredInternalStoragePhotos(
    viewModel: InternalStorageViewModel = hiltViewModel(),
) {
    val filterText = viewModel.filterText.collectAsState()
    val filteredPhotos = viewModel.filteredInternalStoragePhotos

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray), contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
            Text(text = "Upload Picture")

            TextField(value = filterText.value, onValueChange = { filterText ->
                viewModel.filterText.value = filterText
                viewModel.filterInternalStoragePhotos()
            })

            LazyColumn {
                // TODO This does not work when removing an item, it does not update !!!
                items(filteredPhotos.size) {
                    val internalPhoto = filteredPhotos[it]

                    PhotoNameRow(
                        name = internalPhoto.name,
                        image = internalPhoto.bitmap.asImageBitmap(),
                        uploadPicture = {
                            viewModel.uploadPNG(internalPhoto)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoNameRow(name: String, image: ImageBitmap, uploadPicture: ()->Unit) {

        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .border(BorderStroke(1.dp, Color.DarkGray))
                .padding(horizontal = 3.dp)
                .height(70.dp)

        ) {
            Image(
                bitmap = image,
                contentDescription = name,
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
            )
            Text(
                text = name,
                modifier = Modifier
                    .width(270.dp)
            )
            Icon(
                Icons.Default.Upload,
                contentDescription = "Upload",
                modifier = Modifier
                    .clickable {
                        uploadPicture()
                    }
                    .padding(8.dp)
            )
        }


}

@Preview
@Composable
fun FilteredInternalStoragePhotosPreview() {
    FilteredInternalStoragePhotos()
}

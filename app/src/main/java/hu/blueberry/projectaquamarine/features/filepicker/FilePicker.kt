package hu.blueberry.projectaquamarine.features.filepicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.api.services.drive.model.File

@Composable
fun FilePicker(
    viewModel: FolderPickerViewModel = hiltViewModel()
) {
    val folders = viewModel.displayedFiles.collectAsState()


    Column {


        LazyColumn {
            items(folders.value, key = { it.name }) { folder ->
                FileRow(
                    folder, onClick = { viewModel.openFolder(folder = folder)
                    }
                )
            }
        }
        Button(onClick = {viewModel.selectFolder()}) {
            Text(text = "Select Folder")
        }
    }



}

@Composable
fun FileRow(file: File, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                onClick.invoke()
            }
            .fillMaxWidth()
            .background(Color.LightGray)
            .border(BorderStroke(1.dp, Color.DarkGray))
            .padding(horizontal = 3.dp)
            .height(70.dp)
    ) {
        Icon(
            Icons.Default.Star,
            contentDescription = "File Type",
        )
        Text(
            text = file.name,
            modifier = Modifier
                .width(270.dp)
        )
    }
}

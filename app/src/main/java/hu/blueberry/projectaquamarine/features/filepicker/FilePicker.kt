package hu.blueberry.projectaquamarine.features.filepicker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CheckCircle
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
import hu.blueberry.camera.ui.elements.buttons.WideFilledButton
import hu.blueberry.drive.services.DriveService

@Composable
fun FilePicker(
    viewModel: FolderPickerViewModel = hiltViewModel(),
    fileTypes: List<String> = listOf(DriveService.MimeType.FOLDER)
) {
    val folders = viewModel.displayedFiles


    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()){

        LazyColumn {
            items(folders, key = { it.name }) { folder ->
                FileRow(
                    folder, onClick = { viewModel.openFolder(folder = folder)
                    }
                )
            }
        }
        WideFilledButton(
            icon = Icons.Outlined.CheckCircle,
            text = "Select Folder",
            onClick = {viewModel.selectFolder()},
            modifier = Modifier.padding(bottom = 10.dp)
        )
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
            .background(Color.White)
            .border(BorderStroke(1.dp, Color.LightGray))
            .padding(horizontal = 3.dp)
            .height(70.dp)
    ) {

        when(file.mimeType){
            DriveService.MimeType.FOLDER -> Icon(
                Icons.Default.Folder,
                contentDescription = "File Type",
                tint = Color.DarkGray
            )
            DriveService.MimeType.SPREADSHEET -> Icon(
                Icons.Default.Description,
                contentDescription = "Spreadsheet",
                tint = Color.Green
            )
        }

        Text(
            text = file.name,
            modifier = Modifier
                .width(270.dp)
        )
    }
}

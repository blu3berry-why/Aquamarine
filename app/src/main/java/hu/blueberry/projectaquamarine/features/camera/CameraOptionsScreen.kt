package hu.blueberry.projectaquamarine.features.camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hu.blueberry.projectaquamarine.features.elements.buttons.WideFilledButton

@Composable
fun CameraOptionsScreen(
    navigateToTakePhoto: () -> Unit,
    navigateToStoredPictures: () -> Unit,
    navigateToSelectFolder: () -> Unit,
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WideFilledButton (
            icon = Icons.Default.CameraAlt,
            text = "Take Photo",
            onClick = { navigateToTakePhoto() }
        )

        WideFilledButton (
            icon = Icons.Default.Image,
            text = "Stored Pictures",
            onClick = { navigateToStoredPictures() }
        )

        WideFilledButton (
            icon = Icons.Default.OpenInBrowser,
            text = "Select Folder",
            onClick = { navigateToSelectFolder() }
        )


    }

}
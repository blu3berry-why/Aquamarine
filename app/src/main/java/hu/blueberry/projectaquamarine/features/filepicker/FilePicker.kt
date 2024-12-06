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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.api.services.drive.model.File
import hu.blueberry.projectaquamarine.features.elements.buttons.WideFilledButton
import hu.blueberry.drive.services.DriveService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilePicker(
    viewModel: FilePickerViewModel = hiltViewModel(),
    fileTypes: List<String> = listOf(DriveService.MimeType.FOLDER),
    chooseType: String = DriveService.MimeType.FOLDER,
    navigateAfterChoosing: () -> Unit = {}
) {
    val files = viewModel.displayedFiles

    LaunchedEffect(viewModel.acceptedMimeTypes) {
        viewModel.setMimeTypes(fileTypes)
        viewModel.chooseType.value = chooseType
    }

    val chosenType = viewModel.chooseType.collectAsState()
    val currentFolder = viewModel.lastParentDisplayName.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = currentFolder.value) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
            HorizontalDivider()
        },
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn {
                items(files, key = { it.id   }) { file ->
                    FileRow(
                        file = file,
                        onClick = returnFileChoosingOnClick(viewModel, file),
                        navigateAfterChoosing = navigateAfterChoosing
                    )

                }
            }
            if (chosenType.value == DriveService.MimeType.FOLDER) {
                WideFilledButton(
                    icon = Icons.Outlined.CheckCircle,
                    text = "Select Folder",
                    onClick = { viewModel.selectFolder() },
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        }

    }
}

@Composable
fun FileRow(file: File, onClick: () -> Unit, navigateAfterChoosing: () -> Unit) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                onClick.invoke()
                navigateAfterChoosing.invoke()
            }
            .fillMaxWidth()
            .padding(horizontal = 3.dp)
            .height(70.dp)
    ) {

        when (file.mimeType) {
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
    HorizontalDivider()
}

fun returnFileChoosingOnClick(
    viewModel: FilePickerViewModel,
    file: File
): () -> Unit {
    when (file.mimeType) {
        DriveService.MimeType.FOLDER -> return { viewModel.openFolder(file) }
        DriveService.MimeType.SPREADSHEET -> return { viewModel.selectSpreadSheet(file) }
        else -> return {}
    }
}

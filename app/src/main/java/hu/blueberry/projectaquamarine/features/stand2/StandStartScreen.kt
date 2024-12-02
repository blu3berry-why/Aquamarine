package hu.blueberry.projectaquamarine.features.stand2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Poll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import hu.blueberry.projectaquamarine.features.elements.buttons.WideFilledButton
import hu.blueberry.projectaquamarine.features.stand2.StandBasicInformation.StandBasicInformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandStartScreen(
    viewModel: StandStartScreenViewModel = hiltViewModel(),
    navigateToFilePickFolderAndSpreadsheet: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val workingDirectoryState = viewModel.currentWorkingDirectory.collectAsState()
    val spreadsheetName = workingDirectoryState.value?.choosenSpreadSheet?.name ?: "Select a Spreadsheet"


    LaunchedEffect(workingDirectoryState.value) {
        viewModel.getWorkingDirectoryFromDatabase()
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = spreadsheetName) },
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
        },
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding),
        ){

            if (workingDirectoryState.value == null){
                Button(
                    onClick = { navigateToFilePickFolderAndSpreadsheet() },
                ) {
                    Text(text = "Select Spreadsheet")
                }
            } else {
                WideFilledButton(
                    onClick = { navigateToFilePickFolderAndSpreadsheet() },
                    text = "Select Another Spreadsheet",
                    icon = Icons.Default.Poll
                )

                StandBasicInformation()
            }
        }
    }
}


@Preview
@Composable
fun StandStartScreenPreview() {
    StandStartScreen(navigateToFilePickFolderAndSpreadsheet = {})
}
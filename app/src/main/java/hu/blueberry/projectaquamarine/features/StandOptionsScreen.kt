package hu.blueberry.projectaquamarine.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun StandOptionsScreen(
    navigateToProductList: () -> Unit,

    navigateToFilePickFolderAndSpreadsheet: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Button(onClick = { navigateToFilePickFolderAndSpreadsheet.invoke() } ){
            Text(text = "Open Spreadsheet")
        }


    }
}
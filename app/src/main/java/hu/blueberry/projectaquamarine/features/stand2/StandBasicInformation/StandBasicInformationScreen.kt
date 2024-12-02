package hu.blueberry.projectaquamarine.features.stand2.StandBasicInformation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import hu.blueberry.projectaquamarine.features.elements.buttons.WideFilledButton

@Composable
fun StandBasicInformation(
    viewModel: StandBasicInformationViewModel = hiltViewModel()
){


    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        WideFilledButton(
            icon = Icons.Default.Category,
            text = "Products",
            onClick = {}
        )

        WideFilledButton(
            icon = Icons.Default.Scale,
            text = "Scales",
            onClick = {}
        )

        LazyColumn (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            items(
                items = viewModel.storages,
                key = { it.worksheetName },
                ){storage ->
                WideFilledButton(
                    icon = Icons.Default.Inventory2,
                    text = storage.worksheetName,
                    onClick = {}
                )
            }

        }
    }

}
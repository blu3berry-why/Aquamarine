package hu.blueberry.projectaquamarine.features.navigationsuitescaffold

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import hu.blueberry.projectaquamarine.features.camera.CameraOptionsScreen
import hu.blueberry.projectaquamarine.features.SettingsScreen
import hu.blueberry.projectaquamarine.features.stand.StandStartScreen


@Composable
fun MyBottomBarNavigation(
    navigateToTakePhoto: () -> Unit,
    navigateToStoredPictures: () -> Unit,
    navigateToProductList: () -> Unit,
    navigateToSelectFolder: () -> Unit,
    navigateToFilePickFolderAndSpreadsheet: () -> Unit,
    navigateToStorageList:(String) -> Unit,
) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.CAMERA) }


    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                it.icon,
                                contentDescription = stringResource(it.contentDescription),
                                modifier = Modifier.scale(1.2f)
                            )
                            Text(
                                text = stringResource(it.label),
                                fontSize = 14.sp
                            )
                        }
                    },

                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        when (currentDestination) {
            AppDestinations.CAMERA -> CameraOptionsScreen(
                navigateToTakePhoto = navigateToTakePhoto,
                navigateToStoredPictures = navigateToStoredPictures,
                navigateToSelectFolder = navigateToSelectFolder,
            )

            AppDestinations.STAND -> StandStartScreen(
                navigateToFilePickFolderAndSpreadsheet = navigateToFilePickFolderAndSpreadsheet,
                navigateToProductList = navigateToProductList,
                navigateToStorageList = navigateToStorageList
            )

            AppDestinations.SETTINGS -> SettingsScreen()
        }
    }
}
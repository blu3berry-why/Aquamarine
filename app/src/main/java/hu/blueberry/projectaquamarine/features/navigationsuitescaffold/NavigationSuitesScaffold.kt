package hu.blueberry.projectaquamarine.features.navigationsuitescaffold

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import hu.blueberry.camera.ui.CameraOptionsScreen
import hu.blueberry.drinks.feature.StandOptionsScreen
import hu.blueberry.projectaquamarine.features.SettingsScreen
import hu.blueberry.projectaquamarine.navigation.AppNavigation

@Composable
fun MyBottomBarNavigation(
    navigateToTakePhoto: () -> Unit,
    navigateToStoredPictures: () -> Unit,
    navigateToProductList: () -> Unit,
    navigateToSelectFolder: () -> Unit,
) {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.CAMERA) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = stringResource(it.contentDescription)
                        )
                    },
                    label = { Text(stringResource(it.label)) },
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
            AppDestinations.STAND -> StandOptionsScreen(
                navigateToProductList = navigateToProductList,
            )
            AppDestinations.SETTINGS-> SettingsScreen()
        }
    }
}
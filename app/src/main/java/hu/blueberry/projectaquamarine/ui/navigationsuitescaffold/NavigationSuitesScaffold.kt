package hu.blueberry.projectaquamarine.ui.navigationsuitescaffold

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
import hu.blueberry.projectaquamarine.navigation.AppNavigation

@Composable
fun MyBottomBarNavigation() {
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
            AppDestinations.CAMERA -> AppNavigation()
            AppDestinations.STAND -> AppNavigation()
            AppDestinations.SETTINGS-> AppNavigation()
        }
    }
}
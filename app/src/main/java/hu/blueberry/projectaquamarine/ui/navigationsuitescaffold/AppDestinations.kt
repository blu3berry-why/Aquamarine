package hu.blueberry.projectaquamarine.ui.navigationsuitescaffold

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import hu.blueberry.projectaquamarine.R

enum class AppDestinations(
    @StringRes val label: Int,
    val icon: ImageVector,
    @StringRes val contentDescription: Int
) {
    CAMERA(R.string.camera, Icons.Default.CameraAlt, R.string.camera),
    STAND(R.string.stand, Icons.Default.LocalDrink, R.string.stand),
    SETTINGS(R.string.settings, Icons.Default.Settings, R.string.settings)

}
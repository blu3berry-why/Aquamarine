package hu.blueberry.projectaquamarine.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import hu.blueberry.projectaquamarine.R
import hu.blueberry.projectaquamarine.auth.helper.getGoogleSignInClient
import hu.blueberry.projectaquamarine.features.elements.buttons.WideFilledButton

@Composable
fun SettingsScreen(
    onLogout: () -> Unit
){

    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        WideFilledButton(
            icon = Icons.AutoMirrored.Filled.Logout,
            text = stringResource(R.string.logout),
            onClick = {
                getGoogleSignInClient(context).signOut().addOnSuccessListener {
                    onLogout.invoke()
                }

            },
        )
    }


}
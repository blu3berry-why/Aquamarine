package hu.blueberry.projectaquamarine.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import hu.blueberry.projectaquamarine.auth.ButtonGoogleSignIn
import hu.blueberry.projectaquamarine.auth.helper.getGoogleSignInClient
import hu.blueberry.projectaquamarine.navigation.HomeMenuPage

@Composable
fun AuthenticationPage(
    onAuthenticated: () -> Unit
){

    Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        val context = LocalContext.current
        ButtonGoogleSignIn(
            onGoogleSignInCompleted = {
                onAuthenticated()
            },
            onError = {
                Toast.makeText(context, "Error: $it", Toast.LENGTH_LONG).show()
                Log.d("GoogleSignIn", it)
            },
            googleSignInClient = getGoogleSignInClient(LocalContext.current)
        )
    }
}
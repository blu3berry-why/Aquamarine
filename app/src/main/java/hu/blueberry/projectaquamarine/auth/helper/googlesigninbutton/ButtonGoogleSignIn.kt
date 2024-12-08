package hu.blueberry.projectaquamarine.auth.helper.googlesigninbutton


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import hu.blueberry.projectaquamarine.R
import hu.blueberry.projectaquamarine.auth.helper.GoogleSignInContract
import hu.blueberry.projectaquamarine.features.elements.buttons.WideFilledButton


@Composable
fun ButtonGoogleSignIn(
    googleSignInClient: GoogleSignInClient,
    viewModel: GoogleSignInButtonViewModel = hiltViewModel(),
    onError: (String) -> Unit,
    onGoogleSignInCompleted: () -> Unit,
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel) {
        viewModel.checkSignIn(context) { onGoogleSignInCompleted() }
    }

    val googleSignInLauncher =
        rememberLauncherForActivityResult(contract = GoogleSignInContract(googleSignInClient)) { result ->
            viewModel.handle(result, onError, onGoogleSignInCompleted)
        }

    WideFilledButton(
        icon = Icons.AutoMirrored.Filled.Login,
        text = stringResource(R.string.log_in_to_google),
        onClick = { googleSignInLauncher.launch(GoogleSignInButtonViewModel.SIGN_IN_REQUEST_CODE) },
    )
}
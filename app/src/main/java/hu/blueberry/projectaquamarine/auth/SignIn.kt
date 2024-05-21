package hu.blueberry.projectaquamarine.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch

const val CLIENT_ID = "897296513955-etbugvtbv0lspttm49agdbj0od9jl5ku.apps.googleusercontent.com"

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(CLIENT_ID)
        .build()

    return GoogleSignIn.getClient(context, signInOptions)
}

class AuthResultContract(private val googleSignInClient: GoogleSignInClient) :
    ActivityResultContract<Int, Task<GoogleSignInAccount>?>() {
    override fun parseResult(resultCode: Int, intent: Intent?): Task<GoogleSignInAccount>? {
        return when (resultCode) {
            Activity.RESULT_OK -> GoogleSignIn.getSignedInAccountFromIntent(intent)
            2 -> GoogleSignIn.getSignedInAccountFromIntent(intent)
            else -> {
                Log.d("AuthResultContract", resultCode.toString())
                null
            }
        }
    }

    override fun createIntent(context: Context, input: Int): Intent {
        return googleSignInClient.signInIntent.putExtra("input", input)
    }
}

@Composable
fun ButtonGoogleSignIn(
    onGoogleSignInCompleted: (String) -> Unit,
    onError: (String) -> Unit,
    googleSignInClient: GoogleSignInClient,
) {
    val coroutineScope = rememberCoroutineScope()
    val signInRequestCode = 1
    val context = LocalContext.current

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract(googleSignInClient)) {
            try {
                val account = it?.getResult(ApiException::class.java)
                if (account == null) {
                    onError("Account is null")
                } else {
                    coroutineScope.launch {
                        //onGoogleSignInCompleted(account.idToken!!)
                        try {
                            val signedInAccount = it.getResult(ApiException::class.java)
                            val account = GoogleSignIn.getLastSignedInAccount(context)
                            onGoogleSignInCompleted(account!!.idToken!!)
                        }catch (e: Exception){
                            onError(e.message.toString())
                        }



                    }
                }
            } catch (e: ApiException) {
                onError(e.message.toString())
            }
        }

    Button(
        onClick = { authResultLauncher.launch(signInRequestCode) },
        modifier = Modifier
            .width(300.dp)
            .height(45.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(White),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Access using Google",
                color = Black,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}
package hu.blueberry.projectaquamarine.auth.helper

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import hu.blueberry.projectaquamarine.auth.AuthViewModel


fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        //.requestIdToken(AuthViewModel.CLIENT_ID)
        .requestEmail()
        .build()

    return GoogleSignIn.getClient(context, signInOptions)
}

package hu.blueberry.projectaquamarine.auth.helper

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient

data class AuthenticatedUser(
    var token: String? = null,
    var email: String? = null,
    var authUser: GoogleSignInAccount? = null,
)
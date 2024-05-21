package hu.blueberry.drive.base

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudBase @Inject constructor(
    @ApplicationContext val context: Context,
) {
    fun getCredentials(scopes: List<String>): GoogleAccountCredential? {
        GoogleSignIn.getLastSignedInAccount(context)?.let { googleAccount ->
            val credential = GoogleAccountCredential.usingOAuth2(
                context, scopes
            )
            credential.selectedAccount = googleAccount.account!!

            return credential
        }
        return null
    }
}
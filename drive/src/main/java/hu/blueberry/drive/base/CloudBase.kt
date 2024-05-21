package hu.blueberry.drive.base

import android.content.Context

/*@Singleton
class CloudBase @Inject constructor(
    @ApplicationContext val context: Context,
) {

//    val credential: GoogleAccountCredential?
//        get() {
//            GoogleSignIn.getLastSignedInAccount(context)?.let { googleAccount ->
//                val credential = GoogleAccountCredential.usingOAuth2(
//                    context, scopes
//                )
//                credential.selectedAccount = googleAccount.account!!
//
//                return credential
//            }
//            return null
//        }

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






}*/
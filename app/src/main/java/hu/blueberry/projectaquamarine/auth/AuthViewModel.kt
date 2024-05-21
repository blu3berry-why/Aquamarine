package hu.blueberry.projectaquamarine.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.projectaquamarine.auth.helper.AuthenticatedUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(val authenticatedUser: AuthenticatedUser) : ViewModel() {
    companion object{
        const val CLIENT_ID = "897296513955-etbugvtbv0lspttm49agdbj0od9jl5ku.apps.googleusercontent.com"
        const val SIGN_IN_REQUEST_CODE = 1
    }

    fun handle(task: Task<GoogleSignInAccount>?, onError: (String) -> Unit, onSuccess: (String) -> Unit){
        val account = task?.getResult(ApiException::class.java)
        try {
            if (account == null) {
                onError("Account is null")
            } else {
                    try {
                        //val account = GoogleSignIn.getLastSignedInAccount(context)
                        onSuccess(account.idToken!!)
                    }catch (e: Exception){
                        onError(e.message.toString())
                    }

            }
        } catch (e: ApiException) {
            onError(e.message.toString())
        }
    }

}
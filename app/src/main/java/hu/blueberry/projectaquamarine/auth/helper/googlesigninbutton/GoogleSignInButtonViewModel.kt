package hu.blueberry.projectaquamarine.auth.helper.googlesigninbutton

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.blueberry.projectaquamarine.auth.helper.AuthenticatedUser
import javax.inject.Inject


@HiltViewModel
class GoogleSignInButtonViewModel @Inject constructor(val authenticatedUser: AuthenticatedUser) : ViewModel() {
    companion object{
        const val SIGN_IN_REQUEST_CODE = 1
    }

    fun checkSignIn(context: Context, onSuccess: () -> Unit) {
        if(GoogleSignIn.getLastSignedInAccount(context) != null){
            onSuccess()
        }
    }

    fun handle(task: Task<GoogleSignInAccount>?, onError: (String) -> Unit, onSuccess: () -> Unit){
        try {
            val account = task?.getResult(ApiException::class.java)
            if (account == null) {
                onError("Account is null")
            } else {
                onSuccess()
            }
        } catch (e: ApiException) {
            onError(e.message.toString())
        }
    }
}
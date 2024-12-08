package hu.blueberry.drive

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.services.drive.Drive
import hu.blueberry.drive.base.ResourceState
import hu.blueberry.drive.base.handleResponse
import hu.blueberry.drive.permissions.PermissionRequestManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class PermissionHandlingViewModel : ViewModel() {
    companion object {
        const val TAG = "PermissionHandlingViewModel"
    }

    val permissionManager: PermissionRequestManager = PermissionRequestManager()

    private fun requestPremission(exception: Any, repeatRequest: (() -> Unit)?) {
        //Log the error just to be a reminder
        Log.d(TAG, exception.toString())
        //If it is Recoverable we ask for the permission
        if (exception is UserRecoverableAuthIOException) {
            permissionManager.requestPermissionAndRepeatRequest(exception.intent) {

                if (repeatRequest != null) {
                    repeatRequest()
                }
            }
        } else {
            if (exception is Throwable){
                throw exception
            }else {
                throw Exception(exception.toString())
            }
        }
    }

    fun <T> handleUserRecoverableAuthError(
        request: suspend () -> Flow<ResourceState<T>>,
        onError: (error: Any) -> Unit = {},
        onSuccess: (T) -> Unit = {},
        retry: Int = 3,
    ) {
        if (retry == 0) {
            return
        }

        // run on other thread (important for room and other long tasks)
        viewModelScope.launch(Dispatchers.IO) {
            request().collectLatest {
                handleResponse(it,
                    // Sends back to handle success
                    onSuccess = { data -> onSuccess(data) },

                    onError = { error ->
                        try {
                            //If error check if it is UserRecoverableAuthIOException
                            //If it is ask for permission
                            requestPremission(error) {
                                //Repeat the previous request, but the user might not given us the permission
                                //we try again for until reaching zero
                                handleUserRecoverableAuthError(
                                    request,
                                    onError,
                                    onSuccess,
                                    (retry - 1)
                                )
                            }
                        } catch (e: Exception) {
                            //If an exception happens or it is not a UserRecoverableAuthIOException it is gonna end up here
                            //So we Log it and let the user handle it
                            Log.d(TAG, e.toString())
                            onError(e)


                            //When developing the app should crash to see the problem
                            throw e
                        }
                    }
                )
            }
        }
    }

    fun <T> runIO(
        request: suspend () -> Flow<ResourceState<T>>,
        onError: (error: Any) -> Unit = {},
        onSuccess: (T) -> Unit = {},
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            request().collectLatest {
                handleResponse(it,
                    onSuccess = { data -> onSuccess(data) },
                    onError = { error ->
                        onError(error)
                        Log.d(TAG, error.toString())
                    }
                )
            }
        }
    }

    fun runIO(
        onError: (error: Any) -> Unit = {},
        onSuccess: () -> Unit = {},
        request: suspend () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                request()
                onSuccess()
            }catch (e: Exception){
                Log.d(TAG, e.toString())
                onError(e)
                //TODO just for development
                throw e
            }
        }
    }

}
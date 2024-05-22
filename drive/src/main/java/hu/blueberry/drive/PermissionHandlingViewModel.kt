package hu.blueberry.drive


import android.util.Log
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import hu.blueberry.drive.permissions.PermissionRequestManager

interface PermissionHandlingViewModel {
    companion object{
        const val TAG = "PermissionHandlingViewModel"
    }

    val permissionManager: PermissionRequestManager

    fun requestPremission(exception: Any, repeatRequest: (() -> Unit)?){
        Log.d(TAG, exception.toString())
        if (exception is UserRecoverableAuthIOException){
            permissionManager.requestPermissionAndRepeatRequest(exception.intent) {
                if (repeatRequest != null) {
                    repeatRequest()
                }
            }
        }else{
            throw Exception(exception.toString())
        }
    }


}
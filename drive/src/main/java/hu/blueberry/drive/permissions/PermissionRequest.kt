package hu.blueberry.drive.permissions

import android.content.Intent
import kotlinx.coroutines.flow.MutableStateFlow

enum class PermissionRequest{
    NO_REQUESTS, NEEDS_PERMISSION
}
class  PermissionRequestManager(
    var permissionState: MutableStateFlow<PermissionRequest> = MutableStateFlow(PermissionRequest.NO_REQUESTS),
    var onResult: () -> Unit = {}
) {
    lateinit var intent: Intent

    fun requestPermissionAndRepeatRequest(intent: Intent, onResult: () -> Unit){
        this.intent = intent
        this.onResult = onResult
        permissionState.value = PermissionRequest.NEEDS_PERMISSION
    }

    fun permissionHandled(){
        permissionState.value = PermissionRequest.NO_REQUESTS
    }

}
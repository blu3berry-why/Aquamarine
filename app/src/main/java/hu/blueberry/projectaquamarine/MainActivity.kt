package hu.blueberry.projectaquamarine

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import hu.blueberry.camera.ui.TakePhotoAndSetData
import hu.blueberry.projectaquamarine.navigation.navigation


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object{
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
        )
    }

    private fun hasRequiredPermissions(): Boolean{
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it,
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO NOT REAL PERMISSION HANDLING fix ()
        if (!hasRequiredPermissions()){
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }


        setContent {
           // TakePhotoAndSetData()
            navigation()
        }

    }

    @Composable
    fun Content(){



    }



}






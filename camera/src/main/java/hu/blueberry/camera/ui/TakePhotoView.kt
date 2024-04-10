package hu.blueberry.camera.ui

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.blueberry.camera.camera.Camera
import hu.blueberry.camera.viewModel.CameraViewModel
import hu.blueberry.themes.theme.ProjectAquamarineTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakePhotoView (
    applicationContext: Context,
    ){
    ProjectAquamarineTheme {
        val scope = rememberCoroutineScope()
        val viewModel = viewModel<CameraViewModel>()
        val bitmaps by viewModel.bitmaps.collectAsState()

        // A surface container using the 'background' color from the theme
        val scaffoldState = rememberBottomSheetScaffoldState()
        val controller = remember {
            LifecycleCameraController(applicationContext).apply {
                setEnabledUseCases(
                    CameraController.IMAGE_CAPTURE
                    // or CameraController.VIDEO_CAPTURE
                )
            }
        }

        BottomSheetScaffold(
            scaffoldState= scaffoldState,
            sheetPeekHeight = 0.dp,
            sheetContent = {
                PhotoBottomSheetContent(
                    bitmaps = bitmaps,
                    modifier= Modifier.fillMaxWidth())
            }
        ) { padding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding)
            ){
                CameraPreview(
                    controller = controller,
                    modifier = Modifier.fillMaxSize())

                SwapCameraButton(controller = controller)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {

                    OpenScaffoldGalleryButton(
                        scope = scope,
                        scaffoldState = scaffoldState
                    )

                    TakePhotoButton(
                        applicationContext = applicationContext,
                        controller = controller,
                        viewModel = viewModel
                    )

                }
            }
        }
    }
}

@Composable
fun SwapCameraButton(controller: LifecycleCameraController){
    IconButton(
        onClick = {
            controller.cameraSelector =
                if(controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA){
                    CameraSelector.DEFAULT_FRONT_CAMERA
                } else {
                    CameraSelector.DEFAULT_BACK_CAMERA
                }
        }
    ) {
        Icon(imageVector = Icons.Default.Cameraswitch, contentDescription = "Switch Camera")
    }
}

@Composable
fun TakePhotoButton(applicationContext: Context, controller: LifecycleCameraController, viewModel: CameraViewModel){
    IconButton(onClick = {
        Camera.takePhoto(
            applicationContext,
            controller = controller,
            onPhotoTaken = viewModel::onTakePhoto
        )}) {
        Icon(
            imageVector = Icons.Default.PhotoCamera,
            contentDescription = "Take photo"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenScaffoldGalleryButton(scope:CoroutineScope, scaffoldState: BottomSheetScaffoldState){
    IconButton(onClick = {
        scope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }) {
        Icon(
            imageVector = Icons.Default.Photo,
            contentDescription = "Open gallery"
        )
    }
}

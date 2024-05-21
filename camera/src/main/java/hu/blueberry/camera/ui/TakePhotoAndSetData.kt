package hu.blueberry.camera.ui


import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.blueberry.camera.models.enums.PhotoClockType
import hu.blueberry.camera.models.enums.PhotoTakenTime
import hu.blueberry.camera.ui.elements.ExposedDropdownMenuSample
import hu.blueberry.camera.viewModel.CameraViewModel
import hu.blueberry.drive.permissions.ManagePermissionsWithPermissionManager
import hu.blueberry.themes.theme.ProjectAquamarineTheme

@Composable
fun TakePhotoAndSetData(
    viewModel: CameraViewModel = hiltViewModel(),
) {
    val photoName = viewModel.photoName.collectAsState()
    val bitmap = viewModel.bitmap.collectAsState().value
    val nameOfTheEvent = viewModel.nameOfTheEvent.collectAsState()
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
            it?.let { viewModel.onTakePhoto(it) }
        }

    ManagePermissionsWithPermissionManager(permissionManager = viewModel.permissionManager)

    ProjectAquamarineTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Text(text = photoName.value)

                TextField(
                    value = nameOfTheEvent.value,
                    onValueChange = { viewModel.nameOfTheEvent.value = it })

                Box(
                    modifier = Modifier
                        .height(500.dp)
                        .width(400.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (null != bitmap) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(400.dp)
                                    .width(300.dp)
                                    .clip(RoundedCornerShape(10.dp))

                            )
                            Button(onClick = {
                                viewModel.uploadPNG(
                                    onSuccess = {
                                        Toast.makeText(context, "Image saved successfully", Toast.LENGTH_LONG).show()
                                    }
                                )
                                //viewModel.saveImage(context)
                            }

                            ) {
                                Text(text = "Save")
                            }
                        }

                    } else {
                        Button(onClick = {
                            launcher.launch()
                        }) {
                            Text(text = "Take Photo")
                        }
                    }
                }

                ExposedDropdownMenuSample(
                    options = PhotoTakenTime.entries,
                    viewModel.selectedTakenType,
                    viewModel::setPhotoName
                )

                ExposedDropdownMenuSample(
                    PhotoClockType.entries,
                    viewModel.selectedClockType,
                    viewModel::setPhotoName
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewSetPhotoData() {
    TakePhotoAndSetData()
}
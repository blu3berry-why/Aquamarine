package hu.blueberry.camera.ui


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import hu.blueberry.camera.R
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
    val selectedImageUri = viewModel.selectedImageUri.collectAsState()
    val context = LocalContext.current

    //Used https://stackoverflow.com/questions/75387353/activityresultcontracts-takepicture-it-is-always-returning-false-as-a-result
    val fileProvider = stringResource(id = R.string.fileprovider)

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
            viewModel.createTempImageAndSetFilePathAndSelectedUri(viewModel.uri!!)
        }

    ManagePermissionsWithPermissionManager(permissionManager = viewModel.permissionManager)

    ProjectAquamarineTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Text(text = photoName.value)

                Box(
                    modifier = Modifier
                        .height(500.dp)
                        .width(400.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (null != selectedImageUri.value) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            
                            AsyncImage(
                                model = selectedImageUri.value,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(400.dp)
                                    .width(300.dp)
                                    .clip(RoundedCornerShape(10.dp)), contentScale = ContentScale.Crop )

                            Button(onClick = {
                                viewModel.uploadPNG(
                                    onSuccess = {
                                        viewModel.showToastImageHasBeenSaved(context)
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
                            val file = viewModel.createImageFile(context)

                              viewModel.uri = FileProvider.getUriForFile(
                                context,
                                fileProvider,
                                file
                                )


                            launcher.launch(viewModel.uri)
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
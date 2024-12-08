package hu.blueberry.projectaquamarine.features.camera.takephoto


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.Label
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
import hu.blueberry.projectaquamarine.features.elements.ExposedDropdownMenuSample
import hu.blueberry.drive.permissions.ManagePermissionsWithPermissionManager
import hu.blueberry.projectaquamarine.features.camera.CameraViewModel
import hu.blueberry.projectaquamarine.features.elements.buttons.WideFilledButton
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

    val takePictureLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) {
            viewModel.createTempImageAndSetFilePathAndSelectedUri(viewModel.uri!!)
        }

    ManagePermissionsWithPermissionManager(permissionManager = viewModel.permissionManager)


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(30.dp),
                text = photoName.value
            )

            if (selectedImageUri.value != null) {
                /*
                * When an image has been taken already
                * */
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    /*
                    * Image preview
                    * */
                    AsyncImage(
                        model = selectedImageUri.value,
                        contentDescription = null,
                        modifier = Modifier
                            .height(400.dp)
                            .width(300.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )

                    /*
                * Upload Button
                * */
                    WideFilledButton(
                        onClick =
                        {
                            viewModel.uploadPNG(
                                onSuccess = {
                                    viewModel.showToastImageHasBeenSaved(context)
                                }
                            )
                        },
                        text = stringResource(hu.blueberry.projectaquamarine.R.string.upload_photo),
                        icon = Icons.Default.Upload
                    )

                    /*
                    * Take New Picture
                    * */
                    WideFilledButton(
                        onClick =
                        {
                            viewModel.resetPage()
                        },
                        text = stringResource(hu.blueberry.projectaquamarine.R.string.take_new_picture),
                        icon = Icons.Default.PlusOne
                    )


                }

            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 30.dp)
                ) {

                    Column {
                        ExposedDropdownMenuSample(
                            options = PhotoTakenTime.entries,
                            selectedData = viewModel.selectedTakenType,
                            onClick = viewModel::setPhotoName,
                            label = stringResource(hu.blueberry.projectaquamarine.R.string.time)
                        )

                        ExposedDropdownMenuSample(
                            options = PhotoClockType.entries,
                            selectedData = viewModel.selectedClockType,
                            onClick = viewModel::setPhotoName,
                            label = stringResource(hu.blueberry.projectaquamarine.R.string.clock_name)
                        )
                    }

                    /*
               * No image just a button to take one
               * */
                    WideFilledButton(
                        onClick =
                        {
                            val file = viewModel.createImageFile(context)

                            viewModel.uri = FileProvider.getUriForFile(
                                context,
                                fileProvider,
                                file
                            )
                            // Take picture
                            takePictureLauncher.launch(viewModel.uri)
                        },
                        text = stringResource(hu.blueberry.projectaquamarine.R.string.take_photo),
                        icon = Icons.Default.CameraAlt
                    )
                }
            }

        }
    }
}


@Preview
@Composable
fun PreviewSetPhotoData() {
    TakePhotoAndSetData()
}
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.blueberry.camera.models.enums.PhotoClockType
import hu.blueberry.camera.models.enums.PhotoTakenTime
import hu.blueberry.camera.viewModel.CameraViewModel
import hu.blueberry.themes.theme.ProjectAquamarineTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.enums.EnumEntries

@Composable
fun TakePhotoAndSetData(
    viewModel: CameraViewModel = hiltViewModel(),
) {
    val text = viewModel.photoName.collectAsState()

    val bitmaps = viewModel.bitmaps.collectAsState()

    val nameOfTheEvent = viewModel.nameOfTheEvent.collectAsState()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
            it?.let { viewModel.onTakePhoto(it) }
        }


    val asd = viewModel.selectedClockType.collectAsState()
    ProjectAquamarineTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                Text(text = text.value)

                TextField(value = nameOfTheEvent.value, onValueChange ={viewModel.nameOfTheEvent.value = it} )

                val context = LocalContext.current
                val bitmap = bitmaps.value.firstOrNull()

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
                                val success = viewModel.savePhotoToInternalStorage(
                                    filename = viewModel.getPhotoName(),
                                    bitmap = bitmap
                                )
                                if (success) {
                                    Toast.makeText(
                                        context,
                                        "Image: ${viewModel.getPhotoName()} has been saved.",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            }

                            ) {
                                Text(text = "Save")
                            }
                        }


                    } else {
                        Button(onClick = { launcher.launch() }) {
                            Text(text = "Take Photo")
                        }
                    }
                }

                ExposedDropdownMenuSample(
                    options = PhotoTakenTime.entries,
                    viewModel.selectedTakenType,
                    viewModel::setPhotoName
                )

                ExposedDropdownMenuSample(PhotoClockType.entries, viewModel.selectedClockType, viewModel::setPhotoName)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun <T, J : EnumEntries<T>> ExposedDropdownMenuSample(
    options: J,
    selectedData: MutableStateFlow<T>,
    onClick: ()-> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(selectedData.value) }
    val keyboardController = LocalSoftwareKeyboardController.current
    // We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        // Prevents keyboard from showing up
        val myTextInputService = null
        CompositionLocalProvider(
            // You can also provides null to completely disable the default input service.
            LocalTextInputService provides myTextInputService
        ) {
            TextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedOptionText.toString(),
                onValueChange = {},
                label = { Text("Label") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                /*
                * Apparently this hides the keyboard
                * */
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = { keyboardController?.hide() })
            )
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption.toString()) },
                    onClick = {
                        selectedOptionText = selectionOption
                        selectedData.value = selectionOption
                        expanded = false
                        onClick()
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
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
//package hu.blueberry.projectaquamarine.navigation.camera
//
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import hu.blueberry.projectaquamarine.features.camera.CameraOptionsScreen
//import hu.blueberry.projectaquamarine.features.camera.storedpictures.FilteredInternalStoragePhotos
//import hu.blueberry.projectaquamarine.features.camera.takephoto.TakePhotoAndSetData
//import hu.blueberry.projectaquamarine.features.filepicker.FilePicker
//
//
//@Composable
//fun CameraGraph(
//    options: CameraDestinations
//){
//    val cameraNavController  = rememberNavController()
//    NavHost(navController = cameraNavController,
//        startDestination =  when (options){
//        CameraDestinations.CameraOptionsScreen -> CameraOptionsScreen
//        CameraDestinations.TakePhoto -> TakePhoto
//        CameraDestinations.StoredPictures -> StoredPictures
//        CameraDestinations.SelectFolder -> SelectFolder
//    }){
//        composable<CameraOptionsScreen>{
//            CameraOptionsScreen(
//                navigateToTakePhoto = { cameraNavController.navigate(TakePhoto) },
//                navigateToStoredPictures = { cameraNavController.navigate(StoredPictures)},
//                navigateToSelectFolder = {cameraNavController.navigate(SelectFolder)},
//            )
//        }
//
//        composable<TakePhoto> {
//            TakePhotoAndSetData()
//        }
//
//        composable<StoredPictures> {
//            FilteredInternalStoragePhotos()
//        }
//
//        composable<SelectFolder> {
//            FilePicker()
//        }
//    }
//}
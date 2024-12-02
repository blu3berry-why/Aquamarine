package hu.blueberry.projectaquamarine.navigation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import hu.blueberry.drive.services.DriveService
import hu.blueberry.projectaquamarine.features.camera.storedpictures.FilteredInternalStoragePhotos
import hu.blueberry.projectaquamarine.features.camera.takephoto.TakePhotoAndSetData
import hu.blueberry.projectaquamarine.navigation.stand.addStandNestedGraph
import hu.blueberry.projectaquamarine.features.product.old.ProductListPage
import hu.blueberry.projectaquamarine.auth.AuthenticationPage
import hu.blueberry.projectaquamarine.features.HomeMenuPage
import hu.blueberry.projectaquamarine.features.filepicker.FilePicker
import hu.blueberry.projectaquamarine.features.navigationsuitescaffold.MyBottomBarNavigation
import hu.blueberry.projectaquamarine.features.product.old.ProductDetailsPage

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController , startDestination = AuthScreen) {
        composable<AuthScreen> {
            AuthenticationPage {
                navController.navigate(MenuScreen) {
                    popUpTo(AuthScreen) {
                        inclusive = true
                    }
                }
            }
        }


        composable<ScreenB> {
            val args = it.toRoute<ScreenB>()
            Column (modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(text = "${args.name}, ${args.age} years old")
            }
        }

        composable<TakePhoto> {
            TakePhotoAndSetData()
        }

        composable<HomeMenuPage> {
            HomeMenuPage(navController = navController)
        }

        composable<MenuScreen> {
            MyBottomBarNavigation(
                navigateToTakePhoto = { navController.navigate(TakePhoto) },
                navigateToStoredPictures = { navController.navigate(StoredPictures)},
                navigateToProductList = { navController.navigate(ProductList)},
                navigateToSelectFolder = {navController.navigate(SelectFolder)},
                navigateToFilePickFolderAndSpreadsheet = { navController.navigate(SelectFiles(
                    listOf(DriveService.MimeType.FOLDER,
                        DriveService.MimeType.SPREADSHEET)
                ))}
            )
        }

        composable<SelectFolder> {
            FilePicker()
        }


        composable<ProductList> {
            ProductListPage(navController)
        }

        composable<ProductDetails> {
            val args = it.toRoute<ProductDetails>()
            ProductDetailsPage(args.name)
        }

        composable<StoredPictures> {
            FilteredInternalStoragePhotos()
        }

        addStandNestedGraph(navController = navController)

        composable<SelectFiles> {
            val args = it.toRoute<SelectFiles>()
            FilePicker(fileTypes = args.fileTypes)
        }

    }
}

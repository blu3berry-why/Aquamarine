package hu.blueberry.projectaquamarine.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import hu.blueberry.drive.services.DriveService
import hu.blueberry.projectaquamarine.features.camera.takephoto.TakePhotoAndSetData
import hu.blueberry.projectaquamarine.auth.AuthenticationPage
import hu.blueberry.projectaquamarine.features.HomeMenuPage
import hu.blueberry.projectaquamarine.features.camera.storedpictures.FilteredInternalStoragePhotos
import hu.blueberry.projectaquamarine.features.filepicker.FilePicker
import hu.blueberry.projectaquamarine.features.navigationsuitescaffold.MyBottomBarNavigation
import hu.blueberry.projectaquamarine.features.stand.product.details.ProductDetailsScreen
import hu.blueberry.projectaquamarine.features.stand.product.list.ProductListScreen
import hu.blueberry.projectaquamarine.features.stand.storage.details.SingleItemStandDetailsScreen
import hu.blueberry.projectaquamarine.features.stand.storage.list.StorageItemsListScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AuthScreen) {
        composable<AuthScreen> {
            AuthenticationPage {
                navController.navigate(MenuScreen) {
                    popUpTo(AuthScreen) {
                        inclusive = true
                    }
                }
            }
        }

        composable<TakePhoto> {
            TakePhotoAndSetData()
        }

        composable<StoredPictures> {
            FilteredInternalStoragePhotos()
        }

        composable<HomeMenuPage> {
            HomeMenuPage(navController = navController)
        }

        composable<MenuScreen> {
            MyBottomBarNavigation(
                navigateToTakePhoto = { navController.navigate(TakePhoto) },
                navigateToStoredPictures = { navController.navigate(StoredPictures) },
                navigateToProductList = { navController.navigate(ProductListScreen) },
                navigateToSelectFolder = { navController.navigate(SelectFolder) },
                navigateToFilePickFolderAndSpreadsheet = {
                    navController.navigate(
                        route = SelectFiles(
                            fileTypes = listOf(
                                DriveService.MimeType.FOLDER,
                                DriveService.MimeType.SPREADSHEET
                            ),
                            chooseType = DriveService.MimeType.SPREADSHEET
                        ),
                    )
                },
                navigateToStorageList = { navController.navigate(StorageItemList(it))},
                onLogout = { navController.navigate(AuthScreen)}
            )
        }

        composable<SelectFolder> {
            FilePicker(navigateBack = {navController.popBackStack()})
        }

        composable<SelectFiles> {
            val args = it.toRoute<SelectFiles>()
            FilePicker(
                fileTypes = args.fileTypes,
                chooseType = args.chooseType,
                navigateBack = {navController.popBackStack()
                })
        }

        composable<ProductListScreen> {
            ProductListScreen(
                navigateToProduct = { id -> navController.navigate(ProductDetails(id))}
            )
        }

        composable<ProductDetails> {
            val args = it.toRoute<ProductDetails>()
            ProductDetailsScreen(productId = args.id)
        }

        composable<StorageItemList> {
            val args = it.toRoute<StorageItemList>()
            StorageItemsListScreen(
                worksheetName = args.storageName,
                navigateToProduct = { id -> navController.navigate(StorageItemDetails(id))}
            )
        }

        composable<StorageItemDetails> {
            val args = it.toRoute<StorageItemDetails>()
            SingleItemStandDetailsScreen(productId = args.productId)
        }
    }
}

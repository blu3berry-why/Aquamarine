package hu.blueberry.projectaquamarine.navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import hu.blueberry.camera.ui.TakePhotoAndSetData
import hu.blueberry.projectaquamarine.auth.ButtonGoogleSignIn
import hu.blueberry.projectaquamarine.auth.getGoogleSignInClient
import kotlinx.serialization.Serializable

@Composable
fun navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController , startDestination = LoginScreen) {
        composable<LoginScreen> {
            Column (modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally)
            {   
                Button(onClick = {
                    navController.navigate(ScreenB(
                        name = "William",
                        age = 5
                    ))
                }) {
                    Text(text = "Go to next page")
                }
                Button(onClick = {
                    navController.navigate(TakePhoto)
                }) {
                    Text(text = "TakePhoto")
                }


                Button(onClick = {
                    navController.navigate(TakePhoto)
                }) {
                    Text(text = "Google Authentication")
                }

                ButtonGoogleSignIn(
                    onGoogleSignInCompleted = {
                        navController.navigate(TakePhoto)
                                              },
                    onError = {
                            val h = 2;
                              /*TODO*/
                              },
                    googleSignInClient = getGoogleSignInClient(LocalContext.current)
                )
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
    }
}

 @Serializable
object TakePhoto

@Serializable
object LoginScreen

@Serializable
data class ScreenB(
    val name: String?,
    val age: Int
)
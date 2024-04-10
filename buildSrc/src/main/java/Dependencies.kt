

object Dependencies {

    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}"}
    val lifecycle by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}" }
    val composeBom by lazy { "androidx.compose:compose-bom:${Versions.composeBom}" }
    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }
    val material by lazy { "androidx.compose.material3:material3" }
    val composeUi by lazy { "androidx.compose.ui:ui" }
    val composeUiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    val composeMaterialIcons by lazy { "androidx.compose.material:material-icons-extended" }
    val composeTestJUnit4 by lazy { "androidx.compose.ui:ui-test-junit4" }
    val composeTestManifest by lazy { "androidx.compose.ui:ui-test-manifest" }
    val composeUiTooling by lazy { "androidx.compose.ui:ui-tooling" }
    val composeUiGraphics by lazy { "androidx.compose.ui:ui-graphics" }
    val junit by lazy { "junit:junit:${Versions.junit}" }
    val androidTest by lazy { "androidx.test.ext:junit:${Versions.androidTest}" }
    val androidTestEspresso by lazy { "androidx.test.espresso:espresso-core:${Versions.androidTestEspresso}" }
    val activityCompose by lazy { "androidx.activity:activity-compose:${Versions.activityCompose}" }
    val navigationCompose by lazy { "androidx.navigation:navigation-compose:${Versions.navigationCompose}" }

    //Drive
    val googleDrive by lazy { "com.google.apis:google-api-services-drive:${Versions.googleDrive}" }
    val googleSheets by lazy { "com.google.apis:google-api-services-sheets:${Versions.googleSheets}" }

    //Auth
    val googleApiClient by lazy { "com.google.api-client:google-api-client-android:${Versions.googleApiClient}" }
    val googleAuth by lazy { "com.google.auth:google-auth-library-oauth2-http:${Versions.googleAuth}" }

    val playServicesAuth by lazy { "com.google.android.gms:play-services-auth:${Versions.playServicesAuth}" }
    val playServicesDrive by lazy { "com.google.android.gms:play-services-drive:${Versions.playServicesDrive}" }

    //Dependency injection
    val dagger by lazy { "com.google.dagger:hilt-android:${Versions.dagger}" }
    val androidCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.androidCompiler}" }
    val hiltCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}" }

    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}" }

    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }

    //Camera
    val cameraXCore by lazy { "androidx.camera:camera-core:${Versions.cameraX}" }
    val cameraXCamera2 by lazy { "androidx.camera:camera-camera2:${Versions.cameraX}" }
    val cameraXLifecycle by lazy { "androidx.camera:camera-lifecycle:${Versions.cameraX}" }
    val cameraXVideo by lazy { "androidx.camera:camera-video:${Versions.cameraX}" }
    val cameraXView by lazy { "androidx.camera:camera-view:${Versions.cameraX}" }
    val cameraXMLKitVision by lazy { "androidx.camera:camera-mlkit-vision:${Versions.cameraX}" }
    val cameraXExtensions by lazy { "androidx.camera:camera-extensions:${Versions.cameraX}" }

    //lifecycle
    val lifecycleViewModelCompose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}" }

//    val splashScreen by lazy {"androidx.core:core-splashscreen:${Versions.splashScreen}"}
//    val QRCode by lazy {"com.journeyapps:zxing-android-embedded:${Versions.QRCode}"}
//    val QRZxingCore by lazy {"com.google.zxing:core:${Versions.QRZxingCore}"}
//
//    val cameraCamera2 by lazy {"androidx.camera:camera-camera2:${Versions.cameraCamera2}"}
//    val cameraLifecycle by lazy {"androidx.camera:camera-lifecycle:${Versions.cameraLifecycle}"}
//    val cameraView by lazy {"androidx.camera:camera-view:${Versions.cameraView}-alpha02"}
}

object Modules{
//    const val cloud = ":cloud"
    const val camera = ":camera"
    const val themes = ":themes"
}
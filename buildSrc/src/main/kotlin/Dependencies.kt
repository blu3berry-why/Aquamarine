import org.gradle.api.artifacts.dsl.DependencyHandler


object Dependencies {

    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}"}
    val lifecycle by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}" }
    val composeBom by lazy { "androidx.compose:compose-bom:${Versions.composeBom}" }
    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.appcompat}" }
    val material by lazy { "androidx.compose.material3:material3:${Versions.material}" }
    val composeUi by lazy { "androidx.compose.ui:ui" }
    val composeUiToolingPreview by lazy { "androidx.compose.ui:ui-tooling-preview" }
    val composeMaterialIcons by lazy { "androidx.compose.material:material-icons-extended:${Versions.materialIcons}" }
    val composeTestJUnit4 by lazy { "androidx.compose.ui:ui-test-junit4:${Versions.junit4}" }
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
    //val googleAuth by lazy { "com.google.auth:google-auth-library-oauth2-http:${Versions.googleAuth}" }


    val playServicesAuth by lazy { "com.google.android.gms:play-services-auth:${Versions.playServicesAuth}" }
    //val playServicesDrive by lazy { "com.google.android.gms:play-services-drive:${Versions.playServicesDrive}" }


    //hilt
    val hiltAndroid by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltDagger by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }
    val hiltAgp by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}" }


    val hiltNavigationCompose by lazy { "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}" }

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

    //serialization
    val kotlinxSerialization by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serialization}" }

    //room
    val room by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomAnnotationProcessor by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val roomCoroutines by lazy { "androidx.room:room-ktx:${Versions.room}" }

    //Glide
    val coil by lazy { "io.coil-kt:coil-compose:${Versions.coil}" }

    //Scaffold
    val adaptiveNavigationSuite by lazy {"androidx.compose.material3:material3-adaptive-navigation-suite-android:${Versions.adaptiveNavigationSuite}"}

}

fun DependencyHandler.navigation() {
    implementation(Dependencies.kotlinxSerialization)
    implementation(Dependencies.navigationCompose)
}

fun DependencyHandler.camera() {
    implementation(Dependencies.cameraXCore)
    implementation(Dependencies.cameraXCamera2)
    // If you want to additionally use the CameraX Lifecycle library
    implementation(Dependencies.cameraXLifecycle)
    // If you want to additionally use the CameraX VideoCapture library
    implementation(Dependencies.cameraXVideo)
    // If you want to additionally use the CameraX View class
    implementation(Dependencies.cameraXView)
    // If you want to additionally add CameraX ML Kit Vision Integration
    implementation(Dependencies.cameraXMLKitVision)
    // If you want to additionally use the CameraX Extensions library
    implementation(Dependencies.cameraXExtensions)
}

fun DependencyHandler.compose(){
    implementation(Dependencies.activityCompose)
    androidTestImplementation(Dependencies.composeBom)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.material)
    implementation(Dependencies.composeMaterialIcons)
}

fun DependencyHandler.testImplementations(){
    test(Dependencies.junit)
    androidTestImplementation(Dependencies.androidTest)
    androidTestImplementation(Dependencies.androidTestEspresso)
    androidTestImplementation(Dependencies.composeBom)
    androidTestImplementation(Dependencies.composeTestJUnit4)
    debugImplementation(Dependencies.composeUiTooling)
    debugImplementation(Dependencies.composeTestManifest)
}

fun DependencyHandler.hilt(){
    implementation(Dependencies.hiltAndroid)
    kapt(Dependencies.hiltDagger)
    implementation(Dependencies.hiltNavigationCompose)
    implementation(Dependencies.lifecycleViewModelCompose)
}

fun DependencyHandler.core(){
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycle)
}

fun DependencyHandler.auth(){
    implementation(Dependencies.playServicesAuth)
}

fun DependencyHandler.drive(){
    auth()
    implementation(Dependencies.googleApiClient)
    implementation(Dependencies.googleDrive)
    implementation(Dependencies.googleSheets)
}


fun DependencyHandler.room(){
    implementation(Dependencies.kotlinxSerialization)
    implementation(Dependencies.room)
    kapt(Dependencies.roomAnnotationProcessor)
    implementation(Dependencies.roomCoroutines)
}


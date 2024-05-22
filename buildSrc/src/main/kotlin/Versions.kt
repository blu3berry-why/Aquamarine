import org.gradle.api.JavaVersion

object Versions {

    //Base
    const val coreKtx =  "1.12.0"
    const val lifecycle = "2.7.0" //2.6.1
    const val composeBom = "2024.05.00"
    const val appcompat = "1.6.1"
    const val activityCompose = "1.7.2"

    //compose
    const val material = "1.2.1"
    const val junit4 = "1.6.7"
    const val materialIcons= "1.6.7"

    //navigation
    const val navigationCompose = "2.8.0-alpha08"
    const val serialization = "1.6.3"

    const val junit = "4.13.2"
    const val androidTest = "1.1.5"
    const val androidTestEspresso = "3.5.1"

    const val kotlinCompilerExtensionVersion = "1.5.14"

    const val jvmTarget = "17"
    val javaVersion = JavaVersion.VERSION_17


    //Drive
    const val googleDrive = "v3-rev20220815-2.0.0"
    const val googleSheets = "v4-rev20220927-2.0.0"

    //Auth
    const val googleApiClient = "2.0.0"

    const val playServicesAuth = "20.7.0"

    //Dependency injection
    const val hilt = "2.51.1"
    const val hiltNavigationCompose = "1.0.0"
    const val coroutines = "1.7.3"

    //Camera
    const val cameraX = "1.4.0-alpha04"

    //Room
    const val room = "2.6.1"

}
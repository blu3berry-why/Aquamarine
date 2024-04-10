plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "hu.blueberry.projectaquamarine"
    compileSdk = 34

    defaultConfig {
        applicationId = "hu.blueberry.projectaquamarine"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.coreKtx)
    implementation(Dependencies.lifecycle)
    implementation(Dependencies.activityCompose)
    implementation(platform(Dependencies.composeBom))
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeUiTooling)
    implementation(Dependencies.material)
    implementation(Dependencies.composeMaterialIcons)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidTest)
    androidTestImplementation(Dependencies.androidTestEspresso)
    androidTestImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.composeTestJUnit4)
    debugImplementation(Dependencies.composeUiTooling)
    debugImplementation(Dependencies.composeTestManifest)


    implementation(Dependencies.lifecycleViewModelCompose)


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


    //Modules
    implementation(project(Modules.camera))
    implementation(project(Modules.themes))
}
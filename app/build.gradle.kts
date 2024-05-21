plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization")
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
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
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
    compose()

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidTest)
    androidTestImplementation(Dependencies.androidTestEspresso)
    androidTestImplementation(platform(Dependencies.composeBom))
    androidTestImplementation(Dependencies.composeTestJUnit4)
    debugImplementation(Dependencies.composeUiTooling)
    debugImplementation(Dependencies.composeTestManifest)


    implementation(Dependencies.lifecycleViewModelCompose)

    navigation()


    camera()

    implementation(Dependencies.oneTapAuth)
    implementation("com.google.android.gms:play-services-auth:20.4.1")

    //dependency injection
    hilt()

    //navigation compose
    implementation(Dependencies.hiltNavigationCompose)



    //Modules
    cameraModule()
    themesModule()
}
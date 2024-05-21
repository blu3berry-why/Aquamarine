import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Modules{
    //    const val cloud = ":cloud"
    const val camera = ":camera"
    const val themes = ":themes"
    const val app = ":app"
    const val drive = ":drive"
}

fun DependencyHandler.cameraModule(){
    implementation(project(Modules.camera))
}

fun DependencyHandler.themesModule(){
    implementation(project(Modules.themes))
}

fun DependencyHandler.appModule(){
    implementation(project(Modules.app))
}

fun DependencyHandler.driveModule(){
    implementation(project(Modules.drive))
}
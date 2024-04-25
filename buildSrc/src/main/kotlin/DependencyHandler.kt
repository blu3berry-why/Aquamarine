import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler


fun DependencyHandler.implementation(dependecy: String) {
    add("implementation", dependecy)
}

fun DependencyHandler.implementation(dependecy: Dependency) {
    add("implementation", dependecy)
}

fun DependencyHandler.test(dependecy: String) {
    add("testImplementation", dependecy)
}

fun DependencyHandler.androidTestImplementation(dependecy: String) {
    add("androidTestImplementation", dependecy)
}

fun DependencyHandler.debugImplementation(dependecy: String) {
    add("debugImplementation", dependecy)
}

fun DependencyHandler.kapt(dependecy: String) {
    add("kapt", dependecy)
}

applyPluginManagement()
enableFeaturePreview("STABLE_PUBLISHING")
// enable without kotlin-native
// https://github.com/Kotlin/kotlinx.coroutines/issues/564
// enableFeaturePreview("GRADLE_METADATA")

rootProject.name = "library" // please rename to your library name

// region subprojects

fun Settings.includeSubProject(name: String) {
    include(":$name")
    project(":$name").projectDir = File("$rootDir/subprojects/$name")
    project(":$name").buildFileName = "$name.build.gradle.kts"
}

includeSubProject("core-common")
includeSubProject("core-jvm")
includeSubProject("core-android")
includeSubProject("core-ios")
includeSubProject("core-js")

// endregion

// region examples

fun Settings.includeExampleProject(name: String, dir: String) {
    include(":examples:$name")
    project(":examples:$name").projectDir = File("$rootDir/examples/$dir")
    project(":examples:$name").buildFileName = "$name.build.gradle.kts"
}

// includeExampleProject("bar", "bar")

// endregion

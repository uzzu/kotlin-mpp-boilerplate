import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.maven

@Suppress("unused")
object PluginsId {
    // Kotlin MPP
    const val kotlinMultiPlatformCommon = "org.jetbrains.kotlin.platform.common"
    const val kotlinMultiPlatformJvm = "org.jetbrains.kotlin.platform.jvm"
    const val kotlinMultiPlatformAndroid = "org.jetbrains.kotlin.platform.android"
    const val kotlinMultiPlatformNative = "org.jetbrains.kotlin.platform.native"
    const val kotlinMultiPlatformJs = "kotlin-platform-js"

    // Js
    const val nodeGradle = "com.moowork.node"

    // Android
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val androidJunit5 = "de.mannodermaus.android-junit5"

    // Android Kotlin
    const val kotlinAndroid = "kotlin-android"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"

    // misc
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val buildTimeTracker = "net.rdrei.android.buildtimetracker"
}

object PluginClasspath {
    const val kotlin = PluginModules.kotlin
    const val android = PluginModules.android
    const val androidJunit5 = PluginModules.androidJunit5
    const val kotlinNative = PluginModules.kotlinNative
}

private object PluginModules {
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlintPlugin}"
    const val buildTimeTracker = "net.rdrei.android.buildtimetracker:gradle-plugin:${Versions.buildTimeTrackerPlugin}"
    const val kotlinNative = "org.jetbrains.kotlin:kotlin-native-gradle-plugin:${Versions.kotlin}"
    const val node = "com.moowork.gradle:gradle-node-plugin:${Versions.nodeGradlePlugin}"
    const val android = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinAndroidExtensions = "org.jetbrains.com:kotlin-android-extensions:${Versions.kotlin}"
    const val androidJunit5 = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.androidJunit5Plugin}"
}

fun Settings.applyPluginManagement() {
    pluginManagement {
        repositories {
            google()
            mavenCentral()
            maven(url = "https://plugins.gradle.org/m2/")
            jcenter()
        }

        resolutionStrategy {
            eachPlugin {
                when (requested.id.id) {
                    PluginsId.kotlinMultiPlatformCommon,
                    PluginsId.kotlinMultiPlatformAndroid,
                    PluginsId.kotlinMultiPlatformJs -> {
                        useModule(PluginModules.kotlin)
                    }
                    PluginsId.kotlinMultiPlatformNative -> {
                        useModule(PluginModules.kotlinNative)
                    }
                    PluginsId.nodeGradle -> {
                        useModule(PluginModules.node)
                    }
                    PluginsId.androidApplication,
                    PluginsId.androidLibrary -> {
                        useModule(PluginModules.android)
                    }
                    PluginsId.androidJunit5 -> {
                        useModule(PluginModules.androidJunit5)
                    }
                    PluginsId.kotlinAndroid,
                    PluginsId.kotlinAndroidExtensions -> {
                        useModule(PluginModules.kotlinAndroidExtensions)
                    }
                    PluginsId.ktlint -> {
                        useModule(PluginModules.ktlint)
                    }
                    PluginsId.buildTimeTracker -> {
                        useModule(PluginModules.buildTimeTracker)
                    }
                }
            }
        }
    }
}

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.exclude
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories

fun Project.allProjectsRepositories() {
    allprojects {
        repositories {
            google()
            mavenCentral()
            maven(url = "https://jitpack.io")
            jcenter()
        }
    }
}

@Suppress("unused")
object Libs {
    const val kotlinStdlibCommon = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
    const val kotlinStdlibJvm = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinStdlibJs = "org.jetbrains.kotlin:kotlin-stdlib-js:${Versions.kotlin}"
    const val coroutinesCoreCommon = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.kotlinCoroutines}"
    const val coroutinesCoreJvm = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val coroutinesCoreNative = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.kotlinCoroutines}"
    const val coroutinesCoreJs = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${Versions.kotlinCoroutines}"
}

@Suppress("unused")
object ExampleLibs {
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"

    const val hyperionCore = "com.willowtreeapps.hyperion:hyperion-core:${Versions.hyperion}"
    const val hyperionAttr = "com.willowtreeapps.hyperion:hyperion-attr:${Versions.hyperion}"
    const val hyperionMeasurement = "com.willowtreeapps.hyperion:hyperion-measurement:${Versions.hyperion}"
    const val hyperionRecorder = "com.willowtreeapps.hyperion:hyperion-recorder:${Versions.hyperion}"
    const val hyperionPhoenix = "com.willowtreeapps.hyperion:hyperion-phoenix:${Versions.hyperion}"
    const val hyperionCrash = "com.willowtreeapps.hyperion:hyperion-crash:${Versions.hyperion}"
    const val hyperionSharedPreferences = "com.willowtreeapps.hyperion:hyperion-shared-preferences:${Versions.hyperion}"
    const val hyperionTimber = "com.willowtreeapps.hyperion:hyperion-timber:${Versions.hyperion}"

    const val hyperionPlugin = "com.willowtreeapps.hyperion:hyperion-plugin:${Versions.hyperion}"
    const val googleAutoService = "com.google.auto.service:auto-service:${Versions.googleAutoService}"
}

@Suppress("unused")
object TestLibs {
    // kotlin
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"

    // assertion
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val assertkCommon = "com.willowtreeapps.assertk:assertk-common:${Versions.assertk}"
    const val assertkJvm = "com.willowtreeapps.assertk:assertk-jvm:${Versions.assertk}"
    const val assertkJs = "com.willowtreeapps.assertk:assertk-js:${Versions.assertk}"

    // mock
    const val mockkCommon = "io.mockk:mockk-common:${Versions.mockk}"
    const val mockkJvm = "io.mockk:mockk:${Versions.mockk}"
    const val mockkJs = "io.mockk:mockk-js:1.7.17"

    // kotlin-multiplatform-common specific
    const val kotlinTestCommon = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
    const val kotlinTestAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"

    // junit5 test
    const val kotlinTestJunit5  = "org.jetbrains.kotlin:kotlin-test-junit5:${Versions.kotlin}"
    const val junit5 = "org.junit.jupiter:junit-jupiter-api:${Versions.junit5}"
    const val junit5Engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit5}"
    const val junit5Param = "org.junit.jupiter:junit-jupiter-params:${Versions.junit5}"

    // junit4 test
    const val kotlinTestJunit4 = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"

    // js test
    const val kotlinTestJs = "org.jetbrains.kotlin:kotlin-test-js:${Versions.kotlin}"

    // junit5 android specific
    const val junit5PlatformRunner = "org.junit.platform:junit-platform-runner:${Versions.junit5PlatformRunner}"

    // android test
    const val androidxTestCore = "androidx.test:core:${Versions.androidTestCore}"
    const val androidxTestRunner = "androidx.test:runner:${Versions.androidTestRunner}"
    const val androidxTestRules = "androidx.test:rules:${Versions.androidTestRunner}"
    const val androidxTestExtJunit = "androidx.test.ext:junit:${Versions.androidTestExt}"
    const val androidxTestExtTruth = "androidx.test.ext:truth:${Versions.androidTestExt}"
    const val androidTestInstrumentation = "androidx.multidex:multidex-instrumentation:${Versions.androidTestInstrumentation}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
    const val espressoAccessibility = "androidx.test.espresso:espresso-accessibility:${Versions.espresso}"
    const val espressoWeb = "androidx.test.espresso:espresso-web:${Versions.espresso}"
    const val espressoIdlingConcurrent = "androidx.test.espresso.idling:idling-concurrent:${Versions.espresso}"
    const val espressoIdolingResource = "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
}

@Suppress("unused")
object TestRunner {
    const val androidJunit4Runner = "android.support.test.runner.AndroidJUnitRunner"
}

// region helpers

private const val implementation = "implementation"
private const val testImplementation = "testImplementation"
private const val testRuntimeOnly = "testRuntimeOnly"
private const val androidTestImplementation = "androidTestImplementation"
private const val androidTestRuntimeOnly = "androidTestRuntimeOnly"

fun DependencyHandlerScope.multiPlatformCommonTestDependencies() {
    testImplementation(TestLibs.kotlinTestCommon)
    testImplementation(TestLibs.kotlinTestAnnotationsCommon)
    testImplementation(TestLibs.coroutinesTest)
    testImplementation(TestLibs.assertkCommon) {
        exclude(group = "org.jetbrains.kotlin")
    }
    testImplementation(TestLibs.mockkCommon) {
        exclude(group = "org.jetbrains.kotlin")
    }
}

fun DependencyHandlerScope.junit5TestDependencies() {
    testImplementation(TestLibs.kotlinReflect)
    testImplementation(TestLibs.kotlinTestJunit5) {
        exclude(group = "org.junit.jupiter")
    }
    testImplementation(TestLibs.junit5)
    testImplementation(TestLibs.junit5Param)
    testRuntimeOnly(TestLibs.junit5Engine)
    testImplementation(TestLibs.coroutinesTest)
    testImplementation(TestLibs.assertkJvm) {
        exclude(group = "org.jetbrains.kotlin")
    }
    testImplementation(TestLibs.mockkJvm) {
        exclude(group = "org.jetbrains.kotlin")
    }
}

fun DependencyHandlerScope.junit4TestDependencies() {
    testImplementation(TestLibs.kotlinReflect)
    testImplementation(TestLibs.kotlinTestJunit4)
    testImplementation(TestLibs.coroutinesTest)
    testImplementation(TestLibs.assertkJvm) {
        exclude(group = "org.jetbrains.kotlin")
    }
    testImplementation(TestLibs.mockkJvm) {
        exclude(group = "org.jetbrains.kotlin")
    }
}

fun DependencyHandlerScope.jsTestDependencies() {
    testImplementation(TestLibs.kotlinTestJs)
    testImplementation(TestLibs.assertkJs)
    testImplementation(TestLibs.mockkJs)
}

fun DependencyHandlerScope.androidXTestDependencies() {
    androidTestImplementation(TestLibs.kotlinReflect)
    androidTestImplementation(TestLibs.kotlinTestJunit4)
    androidTestImplementation(TestLibs.assertkJvm) {
        exclude(group = "org.jetbrains.kotlin")
    }
    androidTestImplementation(TestLibs.androidxTestCore)
    androidTestImplementation(TestLibs.androidxTestRunner)
    androidTestImplementation(TestLibs.androidxTestRules)
    androidTestImplementation(TestLibs.androidxTestExtJunit)
    androidTestImplementation(TestLibs.androidxTestExtTruth)
    androidTestImplementation(TestLibs.androidTestInstrumentation)
    androidTestImplementation(TestLibs.truth)
    androidTestImplementation(TestLibs.espressoCore)
    androidTestImplementation(TestLibs.espressoContrib)
    androidTestImplementation(TestLibs.espressoIntents)
    androidTestImplementation(TestLibs.espressoAccessibility)
    androidTestImplementation(TestLibs.espressoWeb)
    androidTestImplementation(TestLibs.espressoIdlingConcurrent)
    androidTestImplementation(TestLibs.espressoIdolingResource)
}

// endregion

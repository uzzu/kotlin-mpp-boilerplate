evaluationDependsOn(":core-common")

plugins {
    id(PluginsId.androidLibrary)
    id(PluginsId.kotlinMultiPlatformAndroid)
    id(PluginsId.androidJunit5)
}

dependencies {
    expectedBy(project(":core-common"))
    implementation(Libs.kotlinStdlibJvm)
    implementation(Libs.coroutinesCoreJvm)
    junit5TestDependencies()
    androidXTestDependencies()
}

android {
    compileSdkVersion(AndroidSdk.compileSdkVersion)

    defaultConfig {
        minSdkVersion(AndroidSdk.minSdkVersion)
        targetSdkVersion(AndroidSdk.targetSdkVersion)
        versionName = publishingArtifactVersion
        consumerProguardFiles("proguard-rules.pro")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}
